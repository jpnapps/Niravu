package com.jpndev.niravu.prefs;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.jpndev.niravu.MApp;
import com.jpndev.niravu.MModeData;
import com.jpndev.niravu.utility.LogUtils;
import com.jpndev.utilitylibrary.LogUtilsutility;
import com.jpndev.utilitylibrary.model.MLoginRoot;

import java.util.ArrayList;
import java.util.List;

public class PrefManager {











    public static final String LOGIN_PREFERENCES = "jpn_Login";

    public static final String TYPE = "Type";
    public static final String WEBURL = "webUrl";
    public static final String MOBJECT = "MOBJECT";
    public static final String JSON_KEY = "JSON_KEY";


    String CONSTANT_APP = "app_object";




    Context context;
    private SharedPreferences sharedpreferences;
    SharedPreferences.Editor editor;
    private static PrefManager instance = null;
    PrefManager()
    {

    }

   // http://graph.facebook.com/1156564127777842/picture?type=larg
    public static PrefManager getInstance(Context context) {
        if(instance == null) {
            instance = new PrefManager(context);
        }
        return instance;
    }
    public PrefManager(Context context) {
        super();
        this.context = context;
        sharedpreferences=context.getSharedPreferences(LOGIN_PREFERENCES, Context.MODE_PRIVATE);
        editor=sharedpreferences.edit();
    }

    public SharedPreferences getSPref()
    {
        return sharedpreferences;

    }
 
   
    public String getJsonKey()
    {
        return getSharedString(JSON_KEY,"");
    }






   
    public void saveJsonKey(String jsonString)
    {
        putSharedString(JSON_KEY,jsonString);

    }



    public void saveAPPObject(Object root)
    {
        try {
            Gson gson = new Gson();
            String json = gson.toJson(root);

            putSharedString(CONSTANT_APP, json);
        }catch (Exception e)
        {

        }
    }
    public void saveAPPObject(Object root,String constat)
    {
        try {
            Gson gson = new Gson();
            String json = gson.toJson(root);

            putSharedString(constat, json);
            LogUtils.LOGD("prefs", "saveAPPObject  saved ");
        }catch (Exception e)
        {
            LogUtils.LOGD("prefs", "saveAPPObject  exce  = "+e.getMessage());
        }
    }

    public MApp getAPPObject()
    {
        MApp loginRoot=null;
        try {


            String jsonString= getSharedString(CONSTANT_APP,"");
            Gson gson = new GsonBuilder().setPrettyPrinting().create();

            loginRoot = gson.fromJson(jsonString, MApp.class);
        }catch (Exception e)
        {
            LogUtils.LOGD("d_theme", "getAPPObject  exce  = "+e.getMessage());
        }

       // LogUtils.LOGD("d_theme", "getAPPObject  loginRoot  = "+loginRoot);
        return loginRoot;
    }


    public ArrayList<MModeData> getModes()
    {
        ArrayList<MModeData>  loginRoot=null;
        try {


            String jsonString= getSharedString("MODES","");
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            if(isValid(jsonString))
              loginRoot = gson.fromJson(jsonString, new TypeToken<List<MModeData>>(){}.getType());
         /*   if(loginRoot==null) {
                loginRoot=temp;
                saveModes(temp,"MODES");
            }*/
            LogUtils.LOGD("Modes", "Pref getModes2 items   "+loginRoot.size());
            // loginRoot = gson.fromJson(jsonString, MApp.class);
        }catch (Exception e)
        {
            LogUtils.LOGD("Modes", "Pref Exception "+e.getMessage());
        }

        return loginRoot;
    }

    public ArrayList<MModeData> getModes(ArrayList<MModeData>temp)
    {
        ArrayList<MModeData>  loginRoot=null;
        try {


            String jsonString= getSharedString("MODES","");
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            if(isValid(jsonString))
              loginRoot = gson.fromJson(jsonString, new TypeToken<List<MModeData>>(){}.getType());
            if(loginRoot==null) {
                loginRoot=temp;
                saveModes(temp,"MODES");
            }
            LogUtils.LOGD("Modes", "Pref getModes2 items   "+loginRoot.size());
           // loginRoot = gson.fromJson(jsonString, MApp.class);
        }catch (Exception e)
        {
            LogUtils.LOGD("Modes", "Pref Exception "+e.getMessage());
        }

        return loginRoot;
    }

    public void saveModes(ArrayList<MModeData> root,String constat)
    {
        try {
            Gson gson = new Gson();
            String json = gson.toJson(root);

            putSharedString(constat, json);
        }catch (Exception e)
        {

        }
    }

    public void saveObject(Object root)
    {
        try {
            Gson gson = new Gson();
            String json = gson.toJson(root);

            putSharedString(MOBJECT, json);
        }catch (Exception e)
        {

        }
    }

    public Object getObject(Object root)
    {
        Object loginRoot=null;
        try {


            String jsonString= getSharedString(MOBJECT,"");
            Gson gson = new GsonBuilder().setPrettyPrinting().create();

            loginRoot = gson.fromJson(jsonString, Object.class);
        }catch (Exception e)
        {

        }
        return loginRoot;
    }


    public void saveObject(Object root,String CONSTANT)
    {
        try {
            Gson gson = new Gson();
            String json = gson.toJson(root);

            putSharedString(CONSTANT, json);
        }catch (Exception e)
        {

        }
    }

    public Object getObject(String CONSTANT,String def)
    {
        Object loginRoot=null;
        try {


            String jsonString= getSharedString(CONSTANT,def);
            Gson gson = new GsonBuilder().setPrettyPrinting().create();

            loginRoot = gson.fromJson(jsonString, Object.class);
        }catch (Exception e)
        {

        }
        return loginRoot;
    }


    public void clear()
    {
        if(!isValid(sharedpreferences))
            sharedpreferences =context.getSharedPreferences(LOGIN_PREFERENCES, Context.MODE_PRIVATE);
        if(!isValid(editor))
            editor = sharedpreferences.edit();
        editor.clear();
        editor.commit();
    }

    public void clearSharedAll() {
        if(!isValid(editor))
        {
            if(!isValid(sharedpreferences))
                sharedpreferences =context.getSharedPreferences(LOGIN_PREFERENCES, Context.MODE_PRIVATE);
            editor = sharedpreferences.edit();
        }
        editor.clear().commit();
    }


    public String getTYPE()
    {
        return getSharedString(TYPE,"");

    }








    public String getWEBURL()
    {
        return getSharedString(WEBURL,"");

    }
    public void saveWEBURL(String  classid)
    {
        putSharedString(WEBURL,classid);

    }



    public String getRoleTYpe()
    {
        return getSharedString("Type","");

    }











    public String getSharedString(String KEY, String defValue) {
        if(!isValid(sharedpreferences))
            sharedpreferences =context.getSharedPreferences(LOGIN_PREFERENCES, Context.MODE_PRIVATE);
        if(!isValid(editor))
            editor = sharedpreferences.edit();
        return sharedpreferences.getString(KEY, defValue);
    }

    public void putSharedString(String KEY, String value) {
        if(!isValid(sharedpreferences))
            sharedpreferences =context.getSharedPreferences(LOGIN_PREFERENCES, Context.MODE_PRIVATE);
        if(!isValid(editor))
            editor = sharedpreferences.edit();
        editor.putString(KEY, value).commit();

    }

    public boolean getSharedBoolean(String KEY, boolean defValue) {
        if(!isValid(sharedpreferences))
            sharedpreferences = context.getSharedPreferences(LOGIN_PREFERENCES, Context.MODE_PRIVATE);
        if(!isValid(editor))
            editor = sharedpreferences.edit();
        return sharedpreferences.getBoolean(KEY, defValue);
    }

    public void putSharedBoolean(String KEY, boolean value) {
        if(!isValid(sharedpreferences))
            sharedpreferences = context.getSharedPreferences(LOGIN_PREFERENCES, Context.MODE_PRIVATE);
        if(!isValid(editor))
            editor = sharedpreferences.edit();
        editor.putBoolean(KEY, value).commit();
    }

    public Long getSharedLong(String KEY, long value) {
        if(!isValid(sharedpreferences))
            sharedpreferences =context.getSharedPreferences(LOGIN_PREFERENCES, Context.MODE_PRIVATE);
        if(!isValid(editor))
            editor = sharedpreferences.edit();
        return sharedpreferences.getLong(KEY,value);
    }

    public void putSharedLong(String KEY, long value) {
        if(!isValid(sharedpreferences))
            sharedpreferences =context.getSharedPreferences(LOGIN_PREFERENCES, Context.MODE_PRIVATE);
        if(!isValid(editor))
            editor = sharedpreferences.edit();
        editor.putLong(KEY, value).commit();
    }

    public int getSharedInt(String KEY, int value) {
        if(!isValid(sharedpreferences))
            sharedpreferences =context.getSharedPreferences(LOGIN_PREFERENCES, Context.MODE_PRIVATE);
        if(!isValid(editor))
            editor = sharedpreferences.edit();
        return sharedpreferences.getInt(KEY,value);
    }

    public void putSharedInt(String KEY, int value) {
        if(!isValid(sharedpreferences))
            sharedpreferences =context.getSharedPreferences(LOGIN_PREFERENCES, Context.MODE_PRIVATE);
        if(!isValid(editor))
            editor = sharedpreferences.edit();
        editor.putInt(KEY, value).commit();
    }







    public String getString(String KEY, String defValue) {
        return sharedpreferences.getString(KEY, defValue);
    }

    public void putString(String KEY, String value) {

        editor.putString(KEY, value).commit();
    }


    public Long getLong(String KEY) {
        return sharedpreferences.getLong(KEY,0L);
    }

    public void putLong(String KEY, long value) {

        editor.putLong(KEY, value).commit();
    }




    public String getUpperCaseFirstLetter(String fname) {
        if(isValid(fname)) {
            StringBuilder rackingSystemSb = new StringBuilder(fname.toLowerCase());
            rackingSystemSb.setCharAt(0, Character.toUpperCase(rackingSystemSb.charAt(0)));
            fname = rackingSystemSb.toString();
        }
        return fname;
    }




    public Boolean isValid(Object text)
    {
        if(text!=null)

            return  true;
        return  false;

    }

    public Boolean isValid(int count)
    {
        if(count>0)
            return  true;
        return  false;

    }
    public Boolean isValid(String text)
    {
        if(text!=null)
            if(!text.trim().equalsIgnoreCase(""))
                return  true;
        return  false;

    }
    public Boolean isValid(List list)
    {
        if(list!=null)
            if(list.size()>0)
                return  true;
        return  false;

    }


}
