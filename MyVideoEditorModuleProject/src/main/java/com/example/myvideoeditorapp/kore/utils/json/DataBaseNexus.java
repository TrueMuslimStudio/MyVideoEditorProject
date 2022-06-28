// 
// Decompiled by Procyon v0.5.36
// 

package com.example.myvideoeditorapp.kore.utils.json;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Date;
import java.util.List;
import java.lang.reflect.Method;
import org.json.JSONArray;
import java.util.Collection;
import com.example.myvideoeditorapp.kore.utils.DateHelper;
import com.example.myvideoeditorapp.kore.utils.TLog;
import com.example.myvideoeditorapp.kore.type.ClazzType;
import java.util.Iterator;
import com.example.myvideoeditorapp.kore.utils.ReflectUtils;
import org.json.JSONObject;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;

public class DataBaseNexus
{
    private Map<String, DataBase> a;
    
    public DataBaseNexus(final Class<?> clazz) {
        this.a = new HashMap<String, DataBase>();
        this.a(clazz);
    }
    
    private void a(final Class<?> clazz) {
        if (clazz == null) {
            return;
        }
        for (final Field field : clazz.getFields()) {
            if (!Modifier.isStatic(field.getModifiers())) {
                this.a(field);
            }
        }
    }
    
    private void a(final Field field) {
        final DataBase dataBase = field.getAnnotation(DataBase.class);
        if (dataBase == null) {
            return;
        }
        this.a.put(field.getName(), dataBase);
    }
    
    public void bindJson(final JsonBaseBean jsonBaseBean, final JSONObject jsonObject) {
        if (jsonBaseBean == null || jsonObject == null) {
            return;
        }
        for (final Map.Entry<String, DataBase> entry : this.a.entrySet()) {
            this.a(ReflectUtils.reflectField(jsonBaseBean.getClass(), entry.getKey()), entry.getValue(), jsonBaseBean, jsonObject);
        }
    }
    
    private void a(final Field field, final DataBase dataBase, final JsonBaseBean jsonBaseBean, final JSONObject jsonObject) {
        if (field == null || !jsonObject.has(dataBase.value())) {
            return;
        }
        final ClazzType type = ClazzType.getType(field.getType().hashCode());
        if (type == null) {
            this.b(field, dataBase, jsonBaseBean, jsonObject);
            return;
        }
        try {
            this.a(type, field, dataBase, jsonBaseBean, jsonObject);
        }
        catch (IllegalArgumentException ex) {
            TLog.e(ex);
        }
        catch (IllegalAccessException ex2) {
            TLog.e(ex2);
        }
    }
    
    private void a(final ClazzType clazzType, final Field field, final DataBase dataBase, final JsonBaseBean obj, final JSONObject jsonObject) throws IllegalAccessException {
        switch (clazzType.ordinal()) {
            case 1:
            case 2: {
                field.setInt(obj, jsonObject.optInt(dataBase.value(), 0));
                break;
            }
            case 3:
            case 4: {
                field.setLong(obj, jsonObject.optLong(dataBase.value(), 0L));
                break;
            }
            case 5:
            case 6: {
                field.setFloat(obj, (float)jsonObject.optDouble(dataBase.value(), 0.0));
                break;
            }
            case 7:
            case 8: {
                field.setDouble(obj, jsonObject.optDouble(dataBase.value(), 0.0));
                break;
            }
            case 9:
            case 10: {
                field.setBoolean(obj, jsonObject.optInt(dataBase.value(), 0) > 0);
                break;
            }
            case 11: {
                field.set(obj, jsonObject.optString(dataBase.value()));
                break;
            }
            case 12: {
                field.set(obj, DateHelper.parseDate(jsonObject.optLong(dataBase.value(), 0L)));
                break;
            }
            case 13: {
                field.set(obj, DateHelper.parseGregorianCalendar(jsonObject.optLong(dataBase.value(), 0L)));
                break;
            }
            case 14: {
                field.set(obj, DateHelper.parseCalendar(jsonObject.optLong(dataBase.value(), 0L)));
                break;
            }
        }
    }
    
    private void b(final Field field, final DataBase dataBase, final JsonBaseBean jsonBaseBean, final JSONObject jsonObject) {
        if (Collection.class.isAssignableFrom(field.getType())) {
            this.a(field, jsonBaseBean, JsonHelper.getJsonArrayForDB(dataBase, jsonObject));
            return;
        }
        final JSONObject jsonObjectForDB = JsonHelper.getJsonObjectForDB(dataBase, jsonObject);
        if (Map.class.isAssignableFrom(field.getType())) {
            this.a(field, jsonBaseBean, jsonObjectForDB);
            return;
        }
        ReflectUtils.setFieldValue(field, jsonBaseBean, this.a(field.getType(), jsonObjectForDB));
    }
    
    private void a(final Field field, final JsonBaseBean jsonBaseBean, final JSONArray jsonArray) {
        if (field == null || jsonBaseBean == null || jsonArray == null) {
            return;
        }
        final Class<?> genericCollectionType = ReflectUtils.genericCollectionType(field.getGenericType());
        if (genericCollectionType == null) {
            return;
        }
        final Method method = ReflectUtils.getMethod(field.getType(), "add", Object.class);
        if (method == null) {
            return;
        }
        final Object classInstance = ReflectUtils.classInstance(field.getType());
        if (JsonBaseBean.class.isAssignableFrom(genericCollectionType)) {
            for (int i = 0; i < jsonArray.length(); ++i) {
                final JsonBaseBean a = this.a(genericCollectionType, JsonHelper.getJSONObject(jsonArray, i));
                if (a != null) {
                    ReflectUtils.reflectMethod(method, classInstance, a);
                }
            }
        }
        else {
            for (int j = 0; j < jsonArray.length(); ++j) {
                ReflectUtils.reflectMethod(method, classInstance, jsonArray.opt(j));
            }
        }
        ReflectUtils.setFieldValue(field, jsonBaseBean, classInstance);
    }
    
    private void a(final Field field, final JsonBaseBean jsonBaseBean, final JSONObject jsonObject) {
        if (field == null || jsonBaseBean == null || jsonObject == null || jsonObject.length() == 0) {
            return;
        }
        final List<Class<?>> genericCollectionTypes = ReflectUtils.genericCollectionTypes(field.getGenericType());
        if (genericCollectionTypes == null || genericCollectionTypes.size() != 2) {
            return;
        }
        final Method method = ReflectUtils.getMethod(field.getType(), "put", Object.class, Object.class);
        if (method == null) {
            return;
        }
        final Object classInstance = ReflectUtils.classInstance(field.getType());
        if (classInstance == null) {
            return;
        }
        final JSONArray names = jsonObject.names();
        for (int i = 0; i < names.length(); ++i) {
            ReflectUtils.reflectMethod(method, classInstance, names.optString(i), jsonObject.optString(names.optString(i)));
        }
        ReflectUtils.setFieldValue(field, jsonBaseBean, classInstance);
    }
    
    private JsonBaseBean a(final Class<?> clazz, final JSONObject json) {
        if (clazz == null || json == null) {
            return null;
        }
        if (!JsonBaseBean.class.isAssignableFrom(clazz)) {
            return null;
        }
        final JsonBaseBean jsonBaseBean = ReflectUtils.classInstance(clazz);
        if (jsonBaseBean != null) {
            jsonBaseBean.setJson(json);
        }
        return jsonBaseBean;
    }
    
    public JSONObject buildJson(final JsonBaseBean jsonBaseBean) {
        if (jsonBaseBean == null) {
            return null;
        }
        final JSONObject jsonObject = new JSONObject();
        for (final Map.Entry<String, DataBase> entry : this.a.entrySet()) {
            this.c(ReflectUtils.reflectField(jsonBaseBean.getClass(), entry.getKey()), entry.getValue(), jsonBaseBean, jsonObject);
        }
        return jsonObject;
    }
    
    private void c(final Field field, final DataBase dataBase, final JsonBaseBean jsonBaseBean, final JSONObject jsonObject) {
        if (field == null || jsonBaseBean == null) {
            return;
        }
        final Object fieldValue = ReflectUtils.getFieldValue(field, jsonBaseBean);
        if (fieldValue == null) {
            return;
        }
        final ClazzType type = ClazzType.getType(field.getType().hashCode());
        if (type == null) {
            this.a(field, dataBase, fieldValue, jsonObject);
            return;
        }
        this.a(type, dataBase, fieldValue, jsonObject);
    }
    
    private void a(final Field field, final DataBase dataBase, final Object o, final JSONObject jsonObject) {
        if (o instanceof Collection) {
            this.b(field, dataBase, o, jsonObject);
            return;
        }
        if (o instanceof Map) {
            this.c(field, dataBase, o, jsonObject);
            return;
        }
        this.d(field, dataBase, o, jsonObject);
    }
    
    private void b(final Field field, final DataBase dataBase, final Object o, final JSONObject jsonObject) {
        if (field == null) {
            return;
        }
        final Class<?> genericCollectionType = ReflectUtils.genericCollectionType(field.getGenericType());
        if (genericCollectionType == null) {
            return;
        }
        final JSONArray jsonArray = new JSONArray();
        if (JsonBaseBean.class.isAssignableFrom(genericCollectionType)) {
            final Iterator<JsonBaseBean> iterator = ((Collection)o).iterator();
            while (iterator.hasNext()) {
                jsonArray.put((Object)iterator.next().buildJson());
            }
        }
        else {
            final Iterator<Object> iterator2 = ((Collection)o).iterator();
            while (iterator2.hasNext()) {
                jsonArray.put(iterator2.next());
            }
        }
        JsonHelper.putObject(jsonObject, dataBase.value(), jsonArray);
    }
    
    private void c(final Field field, final DataBase dataBase, final Object o, final JSONObject jsonObject) {
        if (field == null) {
            return;
        }
        JsonHelper.putObject(jsonObject, dataBase.value(), new JSONObject((Map)o));
    }
    
    private void d(final Field field, final DataBase dataBase, final Object o, final JSONObject jsonObject) {
        if (!(o instanceof JsonBaseBean)) {
            return;
        }
        JsonHelper.putObject(jsonObject, dataBase.value(), ((JsonBaseBean)o).buildJson());
    }

    private void a(ClazzType var1, DataBase var2, Object var3, JSONObject var4) {
        switch(var1) {
            case BooleanType:
            case booleanType:
                JsonHelper.putObject(var4, var2.value(), (Boolean)var3 ? "1" : "0");
                break;
            case StringType:
            default:
                JsonHelper.putObject(var4, var2.value(), var3);
                break;
            case DateType:
                JsonHelper.putObject(var4, var2.value(), ((Date)var3).getTime() / 1000L);
                break;
            case GregorianCalendarType:
                JsonHelper.putObject(var4, var2.value(), ((GregorianCalendar)var3).getTimeInMillis() / 1000L);
                break;
            case CalendarType:
                JsonHelper.putObject(var4, var2.value(), ((Calendar)var3).getTimeInMillis() / 1000L);
        }

    }}
