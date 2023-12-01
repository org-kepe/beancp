package org.kepe.beancp.tool;


import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.lang.annotation.Inherited;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.GenericDeclaration;
import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Parameter;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.lang.reflect.WildcardType;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.kepe.beancp.config.BeancpIgnore;
import org.kepe.beancp.config.BeancpProperty;
import org.kepe.beancp.info.BeancpInfo;
import org.kepe.beancp.info.BeancpTypeInfo;


public class BeancpBeanTool {
	static final Type[] EMPTY_TYPE_ARRAY = new Type[]{};


    private static volatile Class RECORD_CLASS;

    public static final String SUPER = "$super$";

    
    public static Class<?> getFinalPublicClass(Class<?> clazz){
    	if(clazz.isPrimitive()) {
    		return clazz;
    	}
    	Class<?> clazz1=clazz;
    	while(true) {
    		if(Modifier.isPublic(clazz1.getModifiers())) {
        		return clazz1;
        	}
    		clazz1=clazz1.getSuperclass();
    	}
    	
    }
    public static Class<?> getFinalProtectedClass(Class<?> clazz){
    	if(clazz.isPrimitive()) {
    		return clazz;
    	}
    	Class<?> clazz1=clazz;
    	while(true) {
    		if(!Modifier.isPrivate(clazz1.getModifiers())) {
        		return clazz1;
        	}
    		clazz1=clazz1.getSuperclass();
    	}
    	
    }
    //private static final Map<Class,BeancpInfo> C_MAP=new ConcurrentHashMap<>();
    public static Type type(Class clazz,Type... typeArguments) {
    	return BeancpTypeInfo.of(clazz).ofTypeArguments(typeArguments).getType();
    }
    
    
    public static int getAccess(Class clazz) {
    	int mod=clazz.getModifiers();
    	if(Modifier.isPublic(mod)) {
			return 0;
		}else if(Modifier.isPrivate(mod)) {
			return 2;
		}else {
			return 1;
		}
    }
    public static int getAccess(Member member) {
    	int mod=member.getModifiers();
		if(Modifier.isPublic(mod)) {
			return 0;
		}else if(Modifier.isPrivate(mod)) {
			return 2;
		}else {
			return 1;
		}
    }
    

    

    public static boolean isRecord(Class objectClass) {
        Class superclass = objectClass.getSuperclass();
        if (superclass == null) {
            return false;
        }

        if (RECORD_CLASS == null) {
            String superclassName = superclass.getName();
            if ("java.lang.Record".equals(superclassName)) {
                RECORD_CLASS = superclass;
                return true;
            } else {
                return false;
            }
        }

        return superclass == RECORD_CLASS;
    }
    public static String setterName(String methodName, int prefixLength) {
        int methodNameLength = methodName.length();
        char[] chars = new char[methodNameLength - prefixLength];
        methodName.getChars(prefixLength, methodNameLength, chars, 0);
        char c0 = chars[0];
        boolean c1UCase = chars.length > 1 && chars[1] >= 'A' && chars[1] <= 'Z';
        if (c0 >= 'A' && c0 <= 'Z' && !c1UCase) {
            chars[0] = (char) (c0 + 32);
        }
        return new String(chars);
    }


    private static String pascal(String methodName, int methodNameLength, int prefixLength) {
        char[] chars = new char[methodNameLength - prefixLength];
        methodName.getChars(prefixLength, methodNameLength, chars, 0);
        char c0 = chars[0];
        if (c0 >= 'a' && c0 <= 'z' && chars.length > 1) {
            chars[0] = (char) (c0 - 32);
        } else if (c0 == '_' && chars.length > 2) {
            char c1 = chars[1];
            if (c1 >= 'a' && c1 <= 'z' && chars[2] >= 'a' && chars[2] <= 'z') {
                chars[1] = (char) (c1 - 32);
            }
        }
        return new String(chars);
    }

  
   
    

    public static Type getFieldType(Type typer, Class<?> raw, Member field, Type fieldType) {
        Class<?> declaringClass;
        if (field == null) {
            declaringClass = null;
        } else {
            declaringClass = field.getDeclaringClass();
        }

        while (raw != Object.class) {
            Type type = typer == null ? null : canonicalize(typer);
            if (declaringClass == raw) {
                return resolve(type, declaringClass, fieldType);
            }
            Type superType = raw.getGenericSuperclass();
            // interface has no generic super class
            if (superType == null) {
                break;
            }
            typer=resolve(type, raw, superType);
            raw = getRawType(typer);
        }
        return null;
    }

    public static Type getParamType(
    		Type typer,
            Class<?> raw,
            Class declaringClass,
            Parameter field,
            Type fieldType
    ) {
        while (raw != Object.class) {
            if (declaringClass == raw) {
                return resolve(canonicalize(typer), declaringClass, fieldType);
            }
            typer=resolve(typer, raw, raw.getGenericSuperclass());
            raw = getRawType(typer);
        }
        return null;
    }

    /**
     * Returns a new parameterized type, applying {@code typeArguments} to
     * {@code rawType} and enclosed by {@code ownerType}.
     *
     * @return a {@link java.io.Serializable serializable} parameterized type.
     */
    public static ParameterizedType newParameterizedTypeWithOwner(
            Type ownerType, Type rawType, Type... typeArguments) {
        return new ParameterizedTypeImpl(ownerType, rawType, typeArguments);
    }

    /**
     * Returns an array type whose elements are all instances of
     * {@code componentType}.
     *
     * @return a {@link java.io.Serializable serializable} generic array type.
     */
    public static GenericArrayType arrayOf(Type componentType) {
        return new GenericArrayTypeImpl(componentType);
    }

    /**
     * Returns a type that represents an unknown type that extends {@code bound}.
     * For example, if {@code bound} is {@code CharSequence.class}, this returns
     * {@code ? extends CharSequence}. If {@code bound} is {@code Object.class},
     * this returns {@code ?}, which is shorthand for {@code ? extends Object}.
     */
    public static WildcardType subtypeOf(Type bound) {
        Type[] upperBounds;
        if (bound instanceof WildcardType) {
            upperBounds = ((WildcardType) bound).getUpperBounds();
        } else {
            upperBounds = new Type[]{bound};
        }
        return new WildcardTypeImpl(upperBounds, EMPTY_TYPE_ARRAY);
    }

    /**
     * Returns a type that represents an unknown supertype of {@code bound}. For
     * example, if {@code bound} is {@code String.class}, this returns {@code ?
     * super String}.
     */
    public static WildcardType supertypeOf(Type bound) {
        Type[] lowerBounds;
        if (bound instanceof WildcardType) {
            lowerBounds = ((WildcardType) bound).getLowerBounds();
        } else {
            lowerBounds = new Type[]{bound};
        }
        return new WildcardTypeImpl(new Type[]{Object.class}, lowerBounds);
    }

    /**
     * Returns a type that is functionally equal but not necessarily equal
     * according to {@link Object#equals(Object) Object.equals()}. The returned
     * type is {@link java.io.Serializable}.
     */
    public static Type canonicalize(Type type) {
        if (type instanceof Class) {
            Class<?> c = (Class<?>) type;
            return c.isArray() ? new GenericArrayTypeImpl(canonicalize(c.getComponentType())) : c;
        } else if (type instanceof ParameterizedType) {
            ParameterizedType p = (ParameterizedType) type;
            return new ParameterizedTypeImpl(p.getOwnerType(),
                    p.getRawType(), p.getActualTypeArguments());
        } else if (type instanceof GenericArrayType) {
            GenericArrayType g = (GenericArrayType) type;
            return new GenericArrayTypeImpl(g.getGenericComponentType());
        } else if (type instanceof WildcardType) {
            WildcardType w = (WildcardType) type;
            return new WildcardTypeImpl(w.getUpperBounds(), w.getLowerBounds());
        } else {
            // type is either serializable as-is or unsupported
            return type;
        }
    }

    public static Class<?> getRawType(Type type) {
        if (type instanceof Class<?>) {
            // type is a normal class.
            return (Class<?>) type;
        } else if (type instanceof ParameterizedType) {
            ParameterizedType parameterizedType = (ParameterizedType) type;

            // I'm not exactly sure why getRawType() returns Type instead of Class.
            // Neal isn't either but suspects some pathological case related
            // to nested classes exists.
            Type rawType = parameterizedType.getRawType();
            checkArgument(rawType instanceof Class);
            return (Class<?>) rawType;
        } else if (type instanceof GenericArrayType) {
            Type componentType = ((GenericArrayType) type).getGenericComponentType();
            return Array.newInstance(getRawType(componentType), 0).getClass();
        } else if (type instanceof TypeVariable) {
            // we could use the variable's bounds, but that won't work if there are multiple.
            // having a raw type that's more general than necessary is okay
            return Object.class;
        } else if (type instanceof WildcardType) {
            return getRawType(((WildcardType) type).getUpperBounds()[0]);
        } else {
            String className = type == null ? "null" : type.getClass().getName();
            throw new IllegalArgumentException("Expected a Class, ParameterizedType, or "
                    + "GenericArrayType, but <" + type + "> is of type " + className);
        }
    }

    static boolean equal(Object a, Object b) {
        return Objects.equals(a, b);
    }

    /**
     * Returns true if {@code a} and {@code b} are equal.
     */
    public static boolean equals(Type a, Type b) {
        if (a == b) {
            // also handles (a == null && b == null)
            return true;
        } else if (a instanceof Class) {
            // Class already specifies equals().
            return a.equals(b);
        } else if (a instanceof ParameterizedType) {
            if (!(b instanceof ParameterizedType)) {
                return false;
            }

            // TODO: save a .clone() call
            ParameterizedType pa = (ParameterizedType) a;
            ParameterizedType pb = (ParameterizedType) b;
            return equal(pa.getOwnerType(), pb.getOwnerType())
                    && pa.getRawType().equals(pb.getRawType())
                    && Arrays.equals(pa.getActualTypeArguments(), pb.getActualTypeArguments());
        } else if (a instanceof GenericArrayType) {
            if (!(b instanceof GenericArrayType)) {
                return false;
            }

            GenericArrayType ga = (GenericArrayType) a;
            GenericArrayType gb = (GenericArrayType) b;
            return equals(ga.getGenericComponentType(), gb.getGenericComponentType());
        } else if (a instanceof WildcardType) {
            if (!(b instanceof WildcardType)) {
                return false;
            }

            WildcardType wa = (WildcardType) a;
            WildcardType wb = (WildcardType) b;
            return Arrays.equals(wa.getUpperBounds(), wb.getUpperBounds())
                    && Arrays.equals(wa.getLowerBounds(), wb.getLowerBounds());
        } else if (a instanceof TypeVariable) {
            if (!(b instanceof TypeVariable)) {
                return false;
            }
            TypeVariable<?> va = (TypeVariable<?>) a;
            TypeVariable<?> vb = (TypeVariable<?>) b;
            return va.getGenericDeclaration() == vb.getGenericDeclaration()
                    && va.getName().equals(vb.getName());
        } else {
            // This isn't a type we support. Could be a generic array type, wildcard type, etc.
            return false;
        }
    }

    static int hashCodeOrZero(Object o) {
        return o != null ? o.hashCode() : 0;
    }

    public static String typeToString(Type type) {
        return type instanceof Class ? ((Class<?>) type).getName() : type.toString();
    }

    /**
     * Returns the generic supertype for {@code supertype}. For example, given a class {@code
     * IntegerSet}, the result for when supertype is {@code Set.class} is {@code Set<Integer>} and the
     * result when the supertype is {@code Collection.class} is {@code Collection<Integer>}.
     */
    static Type getGenericSupertype(Type context, Class<?> rawType, Class<?> toResolve) {
        if (toResolve == rawType) {
            return context;
        }

        // we skip searching through interfaces if unknown is an interface
        if (toResolve.isInterface()) {
            Class<?>[] interfaces = rawType.getInterfaces();
            for (int i = 0, length = interfaces.length; i < length; i++) {
                if (interfaces[i] == toResolve) {
                    return rawType.getGenericInterfaces()[i];
                } else if (toResolve.isAssignableFrom(interfaces[i])) {
                    return getGenericSupertype(rawType.getGenericInterfaces()[i], interfaces[i], toResolve);
                }
            }
        }

        // check our supertypes
        if (rawType != null && !rawType.isInterface()) {
            while (rawType != Object.class) {
                Class<?> rawSupertype = rawType.getSuperclass();
                if (rawSupertype == toResolve) {
                    return rawType.getGenericSuperclass();
                } else if (toResolve.isAssignableFrom(rawSupertype)) {
                    return getGenericSupertype(rawType.getGenericSuperclass(), rawSupertype, toResolve);
                }
                rawType = rawSupertype;
            }
        }

        // we can't resolve this further
        return toResolve;
    }

    public static Type resolve(Type context, Class<?> contextRawType, Type toResolve) {
        return resolve(context, contextRawType, toResolve, new HashMap<>());
    }

    private static Type resolve(Type context, Class<?> contextRawType, Type toResolve,
                                Map<TypeVariable<?>, Type> visitedTypeVariables) {
        // this implementation is made a little more complicated in an attempt to avoid object-creation
        TypeVariable<?> resolving = null;
        while (true) {
            if (toResolve instanceof TypeVariable) {
                TypeVariable<?> typeVariable = (TypeVariable<?>) toResolve;
                Type previouslyResolved = visitedTypeVariables.get(typeVariable);
                if (previouslyResolved != null) {
                    // cannot reduce due to infinite recursion
                    return (previouslyResolved == Void.TYPE) ? toResolve : previouslyResolved;
                }

                // Insert a placeholder to mark the fact that we are in the process of resolving this type
                visitedTypeVariables.put(typeVariable, Void.TYPE);
                if (resolving == null) {
                    resolving = typeVariable;
                }

                toResolve = resolveTypeVariable(context, contextRawType, typeVariable);
                if (toResolve == typeVariable) {
                    break;
                }
            } else if (toResolve instanceof Class && ((Class<?>) toResolve).isArray()) {
                Class<?> original = (Class<?>) toResolve;
                Type componentType = original.getComponentType();
                Type newComponentType = resolve(context, contextRawType, componentType, visitedTypeVariables);
                toResolve = equal(componentType, newComponentType)
                        ? original
                        : arrayOf(newComponentType);
                break;
            } else if (toResolve instanceof GenericArrayType) {
                GenericArrayType original = (GenericArrayType) toResolve;
                Type componentType = original.getGenericComponentType();
                Type newComponentType = resolve(context, contextRawType, componentType, visitedTypeVariables);
                toResolve = equal(componentType, newComponentType)
                        ? original
                        : arrayOf(newComponentType);
                break;
            } else if (toResolve instanceof ParameterizedType) {
                ParameterizedType original = (ParameterizedType) toResolve;
                Type ownerType = original.getOwnerType();
                Type newOwnerType = resolve(context, contextRawType, ownerType, visitedTypeVariables);
                boolean changed = !equal(newOwnerType, ownerType);

                Type[] args = original.getActualTypeArguments();
                for (int t = 0, length = args.length; t < length; t++) {
                    Type resolvedTypeArgument = resolve(context, contextRawType, args[t], visitedTypeVariables);
                    if (!equal(resolvedTypeArgument, args[t])) {
                        if (!changed) {
                            args = args.clone();
                            changed = true;
                        }
                        args[t] = resolvedTypeArgument;
                    }
                }

                toResolve = changed
                        ? newParameterizedTypeWithOwner(newOwnerType, original.getRawType(), args)
                        : original;
                break;
            } else if (toResolve instanceof WildcardType) {
                WildcardType original = (WildcardType) toResolve;
                Type[] originalLowerBound = original.getLowerBounds();
                Type[] originalUpperBound = original.getUpperBounds();

                if (originalLowerBound.length == 1) {
                    Type lowerBound = resolve(context, contextRawType, originalLowerBound[0], visitedTypeVariables);
                    if (lowerBound != originalLowerBound[0]) {
                        toResolve = supertypeOf(lowerBound);
                        break;
                    }
                } else if (originalUpperBound.length == 1) {
                    Type upperBound = resolve(context, contextRawType, originalUpperBound[0], visitedTypeVariables);
                    if (upperBound != originalUpperBound[0]) {
                        toResolve = subtypeOf(upperBound);
                        break;
                    }
                }
                break;
            } else {
                break;
            }
        }
        // ensure that any in-process resolution gets updated with the final result
        if (resolving != null) {
            visitedTypeVariables.put(resolving, toResolve);
        }
        return toResolve;
    }

    static Type resolveTypeVariable(Type context, Class<?> contextRawType, TypeVariable<?> unknown) {
        Class<?> declaredByRaw = declaringClassOf(unknown);

        // we can't reduce this further
        if (declaredByRaw == null) {
            return unknown;
        }

        Type declaredBy = getGenericSupertype(context, contextRawType, declaredByRaw);
        if (declaredBy instanceof ParameterizedType) {
        	Class declaredByClass=getRawType(declaredBy);
        	if(!declaredByRaw.equals(declaredByClass)){
        		try {
        			int index = indexOf(declaredByClass.getTypeParameters(), unknown);
                    return ((ParameterizedType) declaredBy).getActualTypeArguments()[index];
				} catch (NoSuchElementException e) {
					TypeVariable[] tvs=declaredByClass.getTypeParameters();
					for(int i=0;i<tvs.length;i++) {
						TypeVariable tv=tvs[i];
						if(Objects.equals(unknown.getName(), tv.getName())) {
		                    return ((ParameterizedType) declaredBy).getActualTypeArguments()[i];
						}
					}
					return unknown;
				}
        	}
            int index = indexOf(declaredByRaw.getTypeParameters(), unknown);
            return ((ParameterizedType) declaredBy).getActualTypeArguments()[index];
        }

        return unknown;
    }

    private static int indexOf(Object[] array, Object toFind) {
        for (int i = 0, length = array.length; i < length; i++) {
            if (toFind.equals(array[i])) {
                return i;
            }
        }
        throw new NoSuchElementException();
    }

    /**
     * Returns the declaring class of {@code typeVariable}, or {@code null} if it was not declared by
     * a class.
     */
    private static Class<?> declaringClassOf(TypeVariable<?> typeVariable) {
        GenericDeclaration genericDeclaration = typeVariable.getGenericDeclaration();
        return genericDeclaration instanceof Class
                ? (Class<?>) genericDeclaration
                : null;
    }

    static void checkNotPrimitive(Type type) {
        checkArgument(!(type instanceof Class<?>) || !((Class<?>) type).isPrimitive());
    }

    /**
     * Find the first annotation of {@code annotationType} that is either
     * <em>directly present</em>, <em>meta-present</em>, or <em>indirectly
     * present</em> on the supplied {@code element}.
     *
     * <p>If the element is a class and the annotation is neither <em>directly
     * present</em> nor <em>meta-present</em> on the class, this method will additionally search on
     * interfaces implemented by the class before finding an annotation that is <em>indirectly
     * present</em> on the class.
     *
     * @param element the element on which to search for the annotation
     * @param annotationType the annotation type of need to search
     * @param <A> the annotation
     * @return the searched annotation type
     */
    public static <A extends Annotation> A findAnnotation(AnnotatedElement element, Class<A> annotationType) {
        if (annotationType == null) {
            throw new NullPointerException("annotationType must not be null");
        }

        boolean inherited = annotationType.isAnnotationPresent(Inherited.class);
        return findAnnotation(element, annotationType, inherited, new HashSet<>());
    }

    /**
     * If the {@code annotation}'s annotationType is not {@code annotationType}, then to find the
     * first annotation of {@code annotationType} that is either
     * <em>directly present</em>, <em>meta-present</em>, or <em>indirectly
     * present</em> on the supplied {@code element}.
     *
     * @param annotation annotation
     * @param annotationType the annotation type of need to search
     * @param <A> the searched annotation type
     * @return the searched annotation
     */
    @SuppressWarnings("unchecked")
    public static <A extends Annotation> A findAnnotation(Annotation annotation, Class<A> annotationType) {
        if (annotation == null) {
            throw new NullPointerException("annotation must not be null");
        }
        if (annotationType == null) {
            throw new NullPointerException("annotationType must not be null");
        }

        Class<? extends Annotation> annotationTypeClass = annotation.annotationType();
        if (annotationTypeClass == annotationType) {
            return (A) annotation;
        }

        boolean inherited = annotationType.isAnnotationPresent(Inherited.class);
        return findAnnotation(annotationTypeClass, annotationType, inherited, new HashSet<>());
    }

    /**
     * Find the first annotation of {@code annotationType} that is either
     * <em>directly present</em>, <em>meta-present</em>, or <em>indirectly
     * present</em> on the supplied {@code element}.
     *
     * <p>If the element is a class and the annotation is neither <em>directly
     * present</em> nor <em>meta-present</em> on the class, this method will additionally search on
     * interfaces implemented by the class before finding an annotation that is <em>indirectly
     * present</em> on the class.
     *
     * @param element the element on which to search for the annotation
     * @param annotationType the annotation type of need to search
     * @param inherited whether has {@link Inherited}
     * @param visited this annotation whether visited
     * @param <A> the annotation type
     * @return the searched annotation
     */
    private static <A extends Annotation> A findAnnotation(
            AnnotatedElement element,
            Class<A> annotationType,
            boolean inherited,
            Set<Annotation> visited
    ) {
        if (element == null || annotationType == null) {
            return null;
        }

        A annotation = element.getDeclaredAnnotation(annotationType);
        if (annotation != null) {
            return annotation;
        }

        Annotation[] declaredAnnotations = element.getDeclaredAnnotations();
        A directMetaAnnotation = findMetaAnnotation(annotationType, declaredAnnotations, inherited, visited);
        if (directMetaAnnotation != null) {
            return directMetaAnnotation;
        }

        if (element instanceof Class) {
            Class<?> clazz = (Class<?>) element;

            for (Class<?> ifc : clazz.getInterfaces()) {
                if (ifc != Annotation.class) {
                    A annotationOnInterface = findAnnotation(ifc, annotationType, inherited, visited);
                    if (annotationOnInterface != null) {
                        return annotationOnInterface;
                    }
                }
            }

            if (inherited) {
                Class<?> superclass = clazz.getSuperclass();
                if (superclass != null && superclass != Object.class) {
                    A annotationOnSuperclass = findAnnotation(superclass, annotationType, true, visited);
                    if (annotationOnSuperclass != null) {
                        return annotationOnSuperclass;
                    }
                }
            }
        }

        return findMetaAnnotation(annotationType, getAnnotations(element), inherited, visited);
    }

    /**
     * Find meta-present on indirectly present annotations.
     *
     * @param annotationType the annotation type of need to search
     * @param candidates annotations for candidates
     * @param inherited whether has {@link Inherited}
     * @param visited this annotation whether visited
     * @param <A> the annotation type
     * @return the searched annotation
     */
    private static <A extends Annotation> A findMetaAnnotation(
            Class<A> annotationType,
            Annotation[] candidates,
            boolean inherited,
            Set<Annotation> visited
    ) {
        for (Annotation candidateAnnotation : candidates) {
            Class<? extends Annotation> candidateAnnotationType = candidateAnnotation.annotationType();
            String name = candidateAnnotationType.getName();
            boolean isInJavaLangAnnotationPackage = name.startsWith("java.lang.annotation") || name.startsWith("kotlin.");
            if (!isInJavaLangAnnotationPackage && visited.add(candidateAnnotation)) {
                A metaAnnotation = findAnnotation(candidateAnnotationType, annotationType, inherited, visited);
                if (metaAnnotation != null) {
                    return metaAnnotation;
                }
            }
        }
        return null;
    }

    public static Annotation[] getAnnotations(AnnotatedElement element) {
        try {
            return element.getDeclaredAnnotations();
        } catch (Throwable ignored) {
            return new Annotation[0];
        }
    }

    static final class ParameterizedTypeImpl
            implements ParameterizedType, Serializable {
        private final Type ownerType;
        private final Type rawType;
        private final Type[] typeArguments;

        public ParameterizedTypeImpl(Type ownerType, Type rawType, Type... typeArguments) {
            // require an owner type if the raw type needs it
            if (rawType instanceof Class<?>) {
                Class<?> rawTypeAsClass = (Class<?>) rawType;
                boolean isStaticOrTopLevelClass = Modifier.isStatic(rawTypeAsClass.getModifiers())
                        || rawTypeAsClass.getEnclosingClass() == null;
                checkArgument(ownerType != null || isStaticOrTopLevelClass);
            }

            this.ownerType = ownerType == null ? null : canonicalize(ownerType);
            this.rawType = canonicalize(rawType);
            this.typeArguments = typeArguments.clone();
            for (int t = 0, length = this.typeArguments.length; t < length; t++) {
                checkNotPrimitive(this.typeArguments[t]);
                this.typeArguments[t] = canonicalize(this.typeArguments[t]);
            }
        }

        @Override
        public Type[] getActualTypeArguments() {
            return typeArguments.clone();
        }

        @Override
        public Type getRawType() {
            return rawType;
        }

        @Override
        public Type getOwnerType() {
            return ownerType;
        }

        @Override
        public boolean equals(Object other) {
            return other instanceof ParameterizedType
                    && BeancpBeanTool.equals(this, (ParameterizedType) other);
        }

        @Override
        public int hashCode() {
            return Arrays.hashCode(typeArguments)
                    ^ rawType.hashCode()
                    ^ hashCodeOrZero(ownerType);
        }

        @Override
        public String toString() {
            int length = typeArguments.length;
            if (length == 0) {
                return typeToString(rawType);
            }

            StringBuilder stringBuilder = new StringBuilder(30 * (length + 1));
            stringBuilder.append(typeToString(rawType)).append("<").append(typeToString(typeArguments[0]));
            for (int i = 1; i < length; i++) {
                stringBuilder.append(", ").append(typeToString(typeArguments[i]));
            }
            return stringBuilder.append(">").toString();
        }

        private static final long serialVersionUID = 0;
    }

    public static final class GenericArrayTypeImpl
            implements GenericArrayType, Serializable {
        private final Type componentType;

        public GenericArrayTypeImpl(Type componentType) {
            this.componentType = canonicalize(componentType);
        }

        @Override
        public Type getGenericComponentType() {
            return componentType;
        }

        @Override
        public boolean equals(Object o) {
            return o instanceof GenericArrayType
                    && BeancpBeanTool.equals(this, (GenericArrayType) o);
        }

        @Override
        public int hashCode() {
            return componentType.hashCode();
        }

        @Override
        public String toString() {
            return typeToString(componentType) + "[]";
        }

        private static final long serialVersionUID = 0;
    }

    /**
     * The WildcardType interface supports multiple upper bounds and multiple
     * lower bounds. We only support what the Java 6 language needs - at most one
     * bound. If a lower bound is set, the upper bound must be Object.class.
     */
    static final class WildcardTypeImpl
            implements WildcardType, Serializable {
        private final Type upperBound;
        private final Type lowerBound;

        public WildcardTypeImpl(Type[] upperBounds, Type[] lowerBounds) {
            checkArgument(lowerBounds.length <= 1);
            checkArgument(upperBounds.length == 1);

            if (lowerBounds.length == 1) {
                checkNotPrimitive(lowerBounds[0]);
                checkArgument(upperBounds[0] == Object.class);
                this.lowerBound = canonicalize(lowerBounds[0]);
                this.upperBound = Object.class;
            } else {
                checkNotPrimitive(upperBounds[0]);
                this.lowerBound = null;
                this.upperBound = canonicalize(upperBounds[0]);
            }
        }

        @Override
        public Type[] getUpperBounds() {
            return new Type[]{upperBound};
        }

        @Override
        public Type[] getLowerBounds() {
            return lowerBound != null ? new Type[]{lowerBound} : EMPTY_TYPE_ARRAY;
        }

        @Override
        public boolean equals(Object other) {
            return other instanceof WildcardType
                    && BeancpBeanTool.equals(this, (WildcardType) other);
        }

        @Override
        public int hashCode() {
            // this equals Arrays.hashCode(getLowerBounds()) ^ Arrays.hashCode(getUpperBounds());
            return (lowerBound != null ? 31 + lowerBound.hashCode() : 1)
                    ^ (31 + upperBound.hashCode());
        }

        @Override
        public String toString() {
            if (lowerBound != null) {
                return "? super " + typeToString(lowerBound);
            } else if (upperBound == Object.class) {
                return "?";
            } else {
                return "? extends " + typeToString(upperBound);
            }
        }

        private static final long serialVersionUID = 0;
    }

    static void checkArgument(boolean condition) {
        if (!condition) {
            throw new IllegalArgumentException();
        }
    }
    public static boolean isPossMethod(Method method) {
    	BeancpProperty bp=method.getAnnotation(BeancpProperty.class);
    	if(bp==null) {
    		String methodName=method.getName();
    		if(methodName.equals("getClass")) {
    			return false;
    		}
    		if(methodName.startsWith("is")&&methodName.length()>2&&boolean.class==method.getReturnType()&&method.getParameterCount()==0) {
    			return true;
    		}
    		if(methodName.startsWith("is")&&methodName.length()>2&&boolean.class==method.getGenericParameterTypes()[0]&&method.getParameterCount()==1) {
    			return true;
    		}
    		if(methodName.startsWith("get")&&methodName.length()>3&&method.getParameterCount()==0&&method.getReturnType()!=Void.TYPE) {
    			return true;
    		}
    		if(methodName.startsWith("set")&&methodName.length()>3&&method.getParameterCount()==1) {
    			return true;
    		}
    		return false;
    	}
    	if(method.getParameterCount()==0&&method.getReturnType()!=Void.TYPE) {
    		return true;
    	}
    	if(method.getParameterCount()==1) {
    		return true;
    	}
    	return true;
    }
    public static boolean isSetterMethod(Method method) {
    	BeancpIgnore bi=method.getAnnotation(BeancpIgnore.class);
    	if(bi!=null) {
    		return false;
    	}
    	BeancpProperty bp=method.getAnnotation(BeancpProperty.class);
    	if(bp==null) {
    		String methodName=method.getName();
    		if(methodName.startsWith("is")&&methodName.length()>2&&boolean.class==method.getGenericParameterTypes()[0]&&method.getParameterCount()==1) {
    			return true;
    		}
    		if(methodName.startsWith("set")&&methodName.length()>3&&method.getParameterCount()==1) {
    			return true;
    		}
    		return false;
    	}
    	if(bp.value().length==0) {
    		return false;
    	}
    	if(method.getParameterCount()==1) {
    		return true;
    	}
    	return false;
    }
    public static boolean isGetterMethod(Method method) {
    	BeancpIgnore bi=method.getAnnotation(BeancpIgnore.class);
    	if(bi!=null) {
    		return false;
    	}
    	BeancpProperty bp=method.getAnnotation(BeancpProperty.class);
    	if(bp==null) {
    		String methodName=method.getName();
    		if(methodName.startsWith("is")&&methodName.length()>2&&boolean.class==method.getReturnType()&&method.getParameterCount()==0) {
    			return true;
    		}
    		if(methodName.startsWith("get")&&methodName.length()>3&&method.getParameterCount()==0&&method.getReturnType()!=Void.TYPE) {
    			return true;
    		}
    		return false;
    	}
    	if(bp.value().length==0) {
    		return false;
    	}
    	if(method.getParameterCount()==0&&method.getReturnType()!=Void.TYPE) {
    		return true;
    	}
    	return false;
    }
    
    public static boolean isAllowField(Field field) {
    	BeancpIgnore bi=field.getAnnotation(BeancpIgnore.class);
		if(bi!=null) {
			return false;
		}
		BeancpProperty bp=field.getAnnotation(BeancpProperty.class);
		if(bp!=null) {
			return true;
		}
		int mod=field.getModifiers();
		if(Modifier.isTransient(mod)) {
			return false;
		}
		return true;
	}
    public static boolean isProxy(Class<?> clazz) {
        for (Class<?> item : clazz.getInterfaces()) {
            String interfaceName = item.getName();
            switch (interfaceName) {
                case "org.springframework.cglib.proxy.Factory":
                case "javassist.util.proxy.ProxyObject":
                case "org.apache.ibatis.javassist.util.proxy.ProxyObject":
                case "org.hibernate.proxy.HibernateProxy":
                case "org.springframework.context.annotation.ConfigurationClassEnhancer$EnhancedConfiguration":
                case "org.mockito.cglib.proxy.Factory":
                case "net.sf.cglib.proxy.Factory":
                    return true;
                default:
                    break;
            }
        }
        return false;
    }

    

    
}
