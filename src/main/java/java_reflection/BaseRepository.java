package java_reflection;

import java_reflection.annotations.IgnoreReflection;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.*;

public class BaseRepository<T> {

    private final String PREFIX_SP_NAME = "p_";

    // TODO: Update
    // TODO: Delete
    // TODO: FindOne
    // TODO: FindAll

    protected void save(Object object) {
        // TODO: Should Throws Exception from method:  appendParams and others. So make a generic exception for this BaseRepository :)
        Class cls = object.getClass();

        Field[] fields = cls.getDeclaredFields();

        Map<String, Object> inParams = new HashMap<>();

        for(int i = 0; i < fields.length; i++) {
            Field field = fields[i];
            inParams = appendParams(inParams, field, object, getFieldName(field));
        }

        // Instantiate DBConnectionManager with Schema and Package and procedureName
        // Use auto-generated inParams to pass to Stored Procedure


        System.out.println("END ----");
    }

    private Map<String, Object> appendParams(Map<String, Object> inParams, Field field, Object object, String fieldName) {
       try {
           field.setAccessible(true);
           Object fieldValue = field.get(object);

           if (isFieldLeaf(field, object))
           {

               inParams.put(fieldName, fieldValue);

           } else {
               Field[] values = fieldValue.getClass().getDeclaredFields();
               for (int i = 0; i < values.length; i++) {
                   Field value = values[i];
                   Annotation ignoreAnnotation = value.getAnnotation(IgnoreReflection.class);
                   if (ignoreAnnotation == null) {
                       if (!shouldBeTreatedDifferent(value, fieldValue)) {
                           appendParams(inParams, value, fieldValue, appendToFieldName(fieldName, value));
                       } else {
                           // TODO: Throw RunTimeException to handled just by those who wanted it.
                           // Should be ignore, the procedures we're creating are not designing for this.
                           // If you want to save a list of object to an entity, create a particular method for it, iterate the list
                           //   and save them individually
                       }
                   }
               }
           }
       } catch (IllegalAccessException e) {
           e.printStackTrace();
           // TODO: Should Throw Exception here because the operation will not be able to complete successfully.
       }
        return inParams;
    }

    private String appendToFieldName(String name, Field field) {
        int index = PREFIX_SP_NAME.length();

        String newFieldName = field.getName().toLowerCase();

        String first = name.substring(0, index);
        String last = name.substring(index);

        return first + last + "_" + newFieldName;
    }

    private String getFieldName(Field field) {
        return PREFIX_SP_NAME + field.getName().toLowerCase();
    }

    /**
     * Verify if a field should be treated different than tho be process in the same procedure.
     * @param field
     * @param object
     * @return
     */
    private boolean shouldBeTreatedDifferent(Field field, Object object) {
        boolean result = false;

        field.setAccessible(true);
        Class<?> type = field.getType();
        if ( type.isAssignableFrom(List.class) || type.isAssignableFrom(Map.class) || type.isAssignableFrom(Set.class))
        {

            result = true;

        }

        return result;
    }

    /**
     * Verify if the field is considered primitive: boolean, integer, char, short, long, String, Map, Set, List
     * @param field
     * @param object
     * @return
     * @throws IllegalAccessException
     */
    private boolean isFieldLeaf(Field field, Object object) throws IllegalAccessException {
        boolean result = false;
        try {
            field.setAccessible(true);
            Object fieldValue = field.get(object);
            if (fieldValue == boolean.class || fieldValue instanceof Boolean
                    || fieldValue == long.class || fieldValue instanceof Long
                    || fieldValue == int.class  || fieldValue instanceof Integer
                    || fieldValue == short.class || fieldValue instanceof Short
                    || fieldValue instanceof String
                    || fieldValue == null)
            {

                result = true;

            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return result;
    }

}
