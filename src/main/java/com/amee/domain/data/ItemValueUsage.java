package com.amee.domain.data;

import com.amee.domain.ValueUsageType;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

public class ItemValueUsage implements Serializable {

    public final static int NAME_MIN_SIZE = 2;
    public final static int NAME_MAX_SIZE = 255;

    private String name = "";
    private ValueUsageType type = ValueUsageType.UNDEFINED;

    public ItemValueUsage() {
        super();
    }

    public ItemValueUsage(String name) {
        this();
        setName(name);
    }

    public ItemValueUsage(String name, ValueUsageType type) {
        this(name);
        setType(type);
    }

    public ItemValueUsage(JSONObject itemValueUsageObj) {
        this();
        try {
            setName(itemValueUsageObj.getString("name"));
            if (itemValueUsageObj.has("type")) {
                setType(ValueUsageType.valueOf(itemValueUsageObj.getString("type").toUpperCase().trim()));
            }
        } catch (JSONException e) {
            throw new IllegalArgumentException("Caught JSONException whilst parsing itemValueUsageObj.", e);
        }
    }

    /**
     * Two ItemValueUsage instances are considered equal if their name matches (case ignored), along with standard
     * object identity matching.
     *
     * @param o object to compare
     * @return true if the supplied object matches this object
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if ((o == null) || !ItemValueUsage.class.isAssignableFrom(o.getClass())) return false;
        ItemValueUsage itemValueUsage = (ItemValueUsage) o;
        return itemValueUsage.getName().equalsIgnoreCase(getName());
    }

    /**
     * Returns a hash code based on the name  properties.
     *
     * @return the hash code
     */
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 31 * hash + (null == getName() ? 0 : getName().hashCode());
        return hash;
    }

    @Override
    public String toString() {
        return getName();
    }

    public static Set<ItemValueUsage> getItemValueUsages(JSONArray itemValueUsageArr) {
        if (itemValueUsageArr == null) {
            throw new IllegalArgumentException("The itemValueUsageArr argument was null.");
        }
        Set<ItemValueUsage> itemValueUsages = new HashSet<ItemValueUsage>();
        for (int i = 0; i < itemValueUsageArr.length(); i++) {
            try {
                if (!itemValueUsages.add(new ItemValueUsage(itemValueUsageArr.getJSONObject(i)))) {
                    throw new IllegalArgumentException("More than one equivalent ItemValueUsage was supplied.");
                }
            } catch (JSONException e) {
                throw new IllegalArgumentException("Caught JSONException whilst parsing itemValueUsageArr.", e);
            }
        }
        return itemValueUsages;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name == null) {
            name = "";
        }
        this.name = name;
    }

    public ValueUsageType getType() {
        return type;
    }

    public void setType(ValueUsageType type) {
        if (type == null) {
            type = ValueUsageType.UNDEFINED;
        }
        this.type = type;
    }
}