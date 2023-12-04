package hw2;

import java.lang.reflect.Field;
import java.util.UUID;

import seminar2.task3.*;

public class MyQuery {
    public String myDeleteQuery(Class<?> clazz, UUID id) throws IllegalArgumentException, IllegalAccessException {
        StringBuilder query = new StringBuilder("DELETE FROM ");

        if (clazz.isAnnotationPresent(Table.class)) {
            Table tableAnnotation = clazz.getAnnotation(Table.class);
            query.append(tableAnnotation.name()).append(" WHERE ");
 
            Field[] fields = clazz.getDeclaredFields();
            for (Field field : fields) {

                if (field.isAnnotationPresent(Column.class)) {
                    field.setAccessible(true);

                    Column columnAnnotation = field.getAnnotation(Column.class);
                    if (columnAnnotation.primaryKey())
                        query.append(columnAnnotation.name()).append(" = '").append(id.toString()).append("'");
                    break;
                }
            }
            return query.toString();
        } else {
            return null;
        }
    }
}
