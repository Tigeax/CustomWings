package tigeax.customwings.gui.skullAPI;

import java.lang.reflect.Field;

/**
 * An utility class that simplifies reflection in Bukkit plugins.
 *
 * @author Kristian
 */

public final class Reflections {

	public static <T> FieldAccessor<T> getField(Class<?> target, String name, Class<T> fieldType) {
		for (final Field field : target.getDeclaredFields()) {
			if ((name == null || field.getName().equals(name)) && fieldType.isAssignableFrom(field.getType())) {
				field.setAccessible(true);

				// A function for retrieving a specific field value
				return new FieldAccessor<T>() {

					@SuppressWarnings("unchecked")
					@Override
					public T get(Object target) {
						try {
							return (T) field.get(target);
						} catch (IllegalAccessException e) {
							throw new RuntimeException("Cannot access reflection.", e);
						}
					}

					@Override
					public void set(Object target, Object value) {
						try {
							field.set(target, value);
						} catch (IllegalAccessException e) {
							throw new RuntimeException("Cannot access reflection.", e);
						}
					}

					@Override
					public boolean hasField(Object target) {
						// target instanceof DeclaringClass
						return field.getDeclaringClass().isAssignableFrom(target.getClass());
					}
				};
			}
		}

		// Search in parent classes
		if (target.getSuperclass() != null) return getField(target.getSuperclass(), name, fieldType);
		throw new IllegalArgumentException("Cannot find field with type " + fieldType);
	}

	public interface FieldAccessor<T> {

		public T get(Object target);

		public void set(Object target, Object value);

		public boolean hasField(Object target);
	}

}
