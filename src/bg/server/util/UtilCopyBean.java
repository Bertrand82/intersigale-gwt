package bg.server.util;

import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class UtilCopyBean {
	/**
	 * Copy un bean dans un autre bean (D'une class differente)
	 * Copy tous les champs en se basant sur les getteres et les setters.
	 * @param clazz
	 * @param src
	 * @return
	 * @throws Exception
	 */
	public static Object copyTo(Class clazz, Object src) throws Exception {
		Object o = clazz.newInstance();
		Method[] gettersSrc = src.getClass().getMethods();
		for (int i = 0; i < gettersSrc.length; i++) {
			String methodName = gettersSrc[i].getName();
			final String methodNameSetter = getSetter(gettersSrc[i]);
			List<Method> listMethod = Arrays.asList(clazz.getMethods());
			System.out.println("method :" + methodName + " |  setter :" + methodNameSetter);
			if (methodNameSetter != null) {
				Object data = gettersSrc[i].invoke(src, null);
				Class returnType = gettersSrc[i].getReturnType();
				Method method;
				// Leve une exception method =
				// clazz.getMethod(methodNameSetter,returnType);
				method = getMethod(methodNameSetter, returnType, clazz.getMethods());
				if (method != null) {
					method.invoke(o, data);
				}
			}
		}
		return o;
	}

	private static Method getMethod(String methodNameSetter, Class returnType, Method[] methods) {
		for (Method m : methods) {
			if (m.getName().equals(methodNameSetter)) {
				Type[] types = m.getGenericParameterTypes();
				if (types.length == 1) {
					Type type = types[0];
					if (type instanceof Class) {
						if (((Class) type).equals(returnType)) {
							return m;
						}
					}
				}
			}
		}
		return null;
	}

	private static String getSetter(Method method) {
		String methodName = method.getName();
		String methodNameSetter = null;
		if (methodName.equals("getClass")) {
		} else if (methodName.startsWith("get")) {
			methodNameSetter = methodName.replaceFirst("get", "set");
		} else if (methodName.startsWith("is")) {
			methodNameSetter = methodName.replaceFirst("is", "set");
		}
		return methodNameSetter;
	}

}
