package com.codepath.apps.restclienttemplate.models;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcel;

import java.util.ArrayList;
import java.util.List;

@Parcel
public class Entity {

    public String media;

    // Empty constructor for parcelable lib
    public Entity(){}

    public static Entity fromJson(JSONObject jsonObject) throws JSONException {
        Entity entity = new Entity();
        if (!jsonObject.getJSONObject("entitities").has("media"))
        {
            Log.d("Tweet", "No pictures");
            entity.media = "none";
        }
        else
        {
            Log.d("Tweet", jsonObject.getJSONObject("entities").getJSONArray("media").getJSONObject(0).getString("media_url"));
            entity.media = jsonObject.getJSONObject("entities").getJSONArray("media").getJSONObject(0).getString("media_url");
        }
        return entity;
    }
}
