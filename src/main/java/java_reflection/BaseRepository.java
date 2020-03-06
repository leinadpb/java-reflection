package java_reflection;

import java_reflection.annotations.IgnoreReflection;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class BaseRepository<T> {

    protected void save(Object object, String procedureName) {
        Class cls = object.getClass();

        Field[] fields = cls.getDeclaredFields();

        Map<String, Object> inParams = new HashMap<>();

        for(int i = 0; i < fields.length; i++) {
            Field field = fields[i];
            inParams = appendParams(inParams, field, object);
        }

        // Instantiate DBConnectionManager with Schema and Package and procedureName
        // Use auto-generated inParams to pass to Stored Procedure


        System.out.println("END ----");
    }

    private Map<String, Object> appendParams(Map<String, Object> inParams, Field field, Object object) {
       try {
           field.setAccessible(true);
           Object fieldValue = field.get(object);
           if (fieldValue == boolean.class || fieldValue instanceof Boolean
                   || fieldValue == long.class || fieldValue instanceof Long
                   || fieldValue == int.class  || fieldValue instanceof Integer
                   || fieldValue instanceof String || fieldValue == null)
           {

               inParams.put(getFieldName(field), fieldValue);

           } else {
               Field[] values = fieldValue.getClass().getDeclaredFields();
               for (int i = 0; i < values.length; i++) {
                   Field value = values[i];
                   Annotation ignoreAnnotation = value.getAnnotation(IgnoreReflection.class);
                   if (ignoreAnnotation == null) {
                       appendParams(inParams, value, fieldValue);
                   }
               }
           }
       } catch (IllegalAccessException e) {
           e.printStackTrace();
       }
        return inParams;
    }

    private String getFieldName(Field field) {
        return "p_" + field.getName().toLowerCase();
    }

    private Object getFieldValue(Field field, Object object) throws IllegalAccessException {
        Object value;
        if (field.get(object) instanceof Boolean) {
            return field.getBoolean(object);

        } else if (field.get(object) instanceof Integer) {
            return field.getInt(object);

        } else if (field.get(object) instanceof Long) {
            return field.getLong(object);

        } else if (field.get(object) instanceof Byte) {
            return field.getByte(object);

        } else if (field.get(object) instanceof Float) {
            return field.getFloat(object);

        } else if (field.get(object) instanceof Short) {
            return field.getShort(object);
        } else {
            return field.get(object);
        }
    }

}
