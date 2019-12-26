package cl.transbank.util;

import cl.transbank.exception.TransbankException;

import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;

public class BeanUtils {
    private static volatile BeanUtils instance;

    private BeanUtils() {}

    public static BeanUtils getInstance() {
        if (null == instance) {
            synchronized (BeanUtils.class) {
                instance = new BeanUtils();
            }
        }

        return instance;
    }

    public <T> T copyBeanData(T dest, Object source) throws TransbankException {
        if (null == source)
            source = this;

        try {
            final PropertyDescriptor[] propertyDescriptors = Introspector.getBeanInfo(dest.getClass()).getPropertyDescriptors();
            for (PropertyDescriptor pd : propertyDescriptors) {
                if (null != pd.getReadMethod() && null != pd.getWriteMethod()) {
                    PropertyDescriptor origin = null;
                    try {
                        origin = new PropertyDescriptor(pd.getName(), source.getClass());
                    } catch (Exception e) {
                    }
                    if (null != origin) {
                        final Object originValue = origin.getReadMethod().invoke(source);
                        if (pd.getPropertyType() == origin.getPropertyType()) {
                            if (originValue instanceof Collection) {
                                final Type type = pd.getReadMethod().getGenericReturnType();
                                Class destType = null;
                                if (type instanceof ParameterizedType) {
                                    ParameterizedType pType = (ParameterizedType) type;
                                    final Type[] typeArguments = pType.getActualTypeArguments();
                                    if (null != typeArguments && typeArguments.length > 0 && typeArguments[0] instanceof Class)
                                        destType = (Class) typeArguments[0];
                                }

                                if (null != destType) {
                                    Collection c = (Collection) originValue;
                                    Collection listDest = new ArrayList();
                                    for (Object o : c) {
                                        listDest.add(copyBeanData(destType.getDeclaredConstructor(dest.getClass()).newInstance(dest), o));
                                    }
                                    pd.getWriteMethod().invoke(dest, listDest);
                                }
                            } else {
                                pd.getWriteMethod().invoke(dest, originValue);
                            }
                        } else {
                            final Object destValue = pd.getPropertyType().newInstance();
                            pd.getWriteMethod().invoke(dest, copyBeanData(destValue, originValue));
                        }
                    }
                }
            }

            return dest;
        } catch (NoSuchMethodException | IllegalAccessException |IntrospectionException |InstantiationException |InvocationTargetException e) {
            throw new TransbankException(e);
        }
    }
}
