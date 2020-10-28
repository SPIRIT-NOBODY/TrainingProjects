package part_four.task_three;

import java.lang.reflect.Method;
/**
 * 3. Отобразить все методы с одним параметром класса Math. По имени метода с
 * одним параметром класса Math отобразить его значение.
 */
public class Task03 {
	public static void main(String[] args) {
		Method[] mathMethods = Math.class.getDeclaredMethods();
		String methodString = "Method \"%s\" has one parameter. Parameter type is \"%s\". Return type is \"%s\"";
		for (int i = 0; i < mathMethods.length; i++) {
			Method method = mathMethods[i];
			if (method.getParameterCount() == 1) {
				System.out.println(String.format(methodString, method.getName(),method.getParameterTypes()[0], method.getReturnType()));
			}
		}
	}
}
