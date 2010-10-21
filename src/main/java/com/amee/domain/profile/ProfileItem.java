package com.amee.domain.profile;

import com.amee.domain.Builder;
import com.amee.domain.IProfileItemService;
import com.amee.domain.ObjectType;
import com.amee.domain.data.DataCategory;
import com.amee.domain.data.DataItem;
import com.amee.domain.data.Item;
import com.amee.domain.item.profile.NuProfileItem;
import com.amee.platform.science.ReturnValues;
import com.amee.platform.science.StartEndDate;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowire;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.util.Date;

@Configurable(autowire = Autowire.BY_TYPE)
public class ProfileItem extends Item {

    public final static boolean USE_NU = true;

    @Autowired
    private IProfileItemService profileItemService;

    private LegacyProfileItem legacyEntity;
    private NuProfileItem nuEntity;

    public ProfileItem() {
        super();
        if (USE_NU) {
            setNuEntity(new NuProfileItem());
        } else {
            setLegacyEntity(new LegacyProfileItem());
        }
    }

    public ProfileItem(Profile profile, DataItem dataItem) {
        super();
        if (USE_NU) {
            setNuEntity(new NuProfileItem(profile, dataItem.getNuEntity()));
        } else {
            setLegacyEntity(new LegacyProfileItem(profile, dataItem.getLegacyEntity()));
        }
    }

    public ProfileItem(Profile profile, DataCategory dataCategory, DataItem dataItem) {
        super();
        if (USE_NU) {
            setNuEntity(new NuProfileItem(profile, dataCategory, dataItem.getNuEntity()));
        } else {
            setLegacyEntity(new LegacyProfileItem(profile, dataCategory, dataItem.getLegacyEntity()));
        }
    }

    public ProfileItem(LegacyProfileItem profileItem) {
        super();
        setLegacyEntity(profileItem);
    }

    public ProfileItem(NuProfileItem profileItem) {
        super();
        setNuEntity(profileItem);
    }

    public static ProfileItem getProfileItem(LegacyProfileItem profileItem) {
        if (profileItem != null) {
            if (profileItem.getAdapter() != null) {
                return profileItem.getAdapter();
            } else {
                return new ProfileItem(profileItem);
            }
        } else {
            return null;
        }
    }

    public static ProfileItem getProfileItem(NuProfileItem profileItem) {
        if (profileItem != null) {
            if (profileItem.getAdapter() != null) {
                return profileItem.getAdapter();
            } else {
                return new ProfileItem(profileItem);
            }
        } else {
            return null;
        }
    }

    public void setBuilder(Builder builder) {
        if (isLegacy()) {
            getLegacyEntity().setBuilder(builder);
        } else {
            // TODO
            throw new UnsupportedOperationException();
        }
    }

    public ProfileItem getCopy() {
        if (isLegacy()) {
            return ProfileItem.getProfileItem(getLegacyEntity().getCopy());
        } else {
            return ProfileItem.getProfileItem(getNuEntity().getCopy());
        }
    }

    @Override
    public String getPath() {
        if (isLegacy()) {
            return getLegacyEntity().getPath();
        } else {
            return getNuEntity().getPath();
        }
    }

    public Profile getProfile() {
        if (isLegacy()) {
            return getLegacyEntity().getProfile();
        } else {
            return getNuEntity().getProfile();
        }
    }

    public void setProfile(Profile profile) {
        if (isLegacy()) {
            getLegacyEntity().setProfile(profile);
        } else {
            getNuEntity().setProfile(profile);
        }
    }

    public DataItem getDataItem() {
        if (isLegacy()) {
            return DataItem.getDataItem(getLegacyEntity().getDataItem());
        } else {
            return DataItem.getDataItem(getNuEntity().getDataItem());
        }
    }

    public void setDataItem(DataItem dataItem) {
        if (isLegacy()) {
            getLegacyEntity().setDataItem(dataItem.getLegacyEntity());
        } else {
            getNuEntity().setDataItem(dataItem.getNuEntity());
        }
    }

    public StartEndDate getStartDate() {
        if (isLegacy()) {
            return getLegacyEntity().getStartDate();
        } else {
            return getNuEntity().getStartDate();
        }
    }

    public void setStartDate(Date startDate) {
        if (isLegacy()) {
            getLegacyEntity().setStartDate(startDate);
        } else {
            getNuEntity().setStartDate(startDate);
        }
    }

    @Override
    public StartEndDate getEndDate() {
        if (isLegacy()) {
            return getLegacyEntity().getEndDate();
        } else {
            return getNuEntity().getEndDate();
        }
    }

    public void setEndDate(Date endDate) {
        if (isLegacy()) {
            getLegacyEntity().setEndDate(endDate);
        } else {
            getNuEntity().setEndDate(endDate);
        }
    }

    public boolean isEnd() {
        if (isLegacy()) {
            return getLegacyEntity().isEnd();
        } else {
            return getNuEntity().isEnd();
        }
    }

    public ReturnValues getAmounts(boolean recalculate) {
        if (isLegacy()) {
            return getLegacyEntity().getAmounts(recalculate);
        } else {
            return getItemService().getAmounts(getNuEntity(), recalculate);
        }
    }

    public ReturnValues getAmounts() {
        return getAmounts(false);
    }

    @Deprecated
    public double getAmount() {
        if (isLegacy()) {
            return getLegacyEntity().getAmount();
        } else {
            return getItemService().getAmount(getNuEntity());
        }
    }

    public void setAmounts(ReturnValues amounts) {
        if (isLegacy()) {
            getLegacyEntity().setAmounts(amounts);
        } else {
            // TODO
            throw new UnsupportedOperationException();
        }
    }

    @Override
    public JSONObject getJSONObject(boolean b) throws JSONException {
        if (isLegacy()) {
            return getLegacyEntity().getJSONObject(b);
        } else {
            // TODO
            throw new UnsupportedOperationException();
        }
    }

    public Element getElement(Document document, boolean b) {
        if (isLegacy()) {
            return getLegacyEntity().getElement(document, b);
        } else {
            // TODO
            throw new UnsupportedOperationException();
        }
    }

    public boolean hasNonZeroPerTimeValues() {
        if (isLegacy()) {
            return getLegacyEntity().hasNonZeroPerTimeValues();
        } else {
            return getItemService().hasNonZeroPerTimeValues(getNuEntity());
        }
    }

    public boolean isSingleFlight() {
        if (isLegacy()) {
            return getLegacyEntity().isSingleFlight();
        } else {
            return getItemService().isSingleFlight(getNuEntity());
        }
    }

    @Override
    public boolean isTrash() {
        if (isLegacy()) {
            return getLegacyEntity().isTrash();
        } else {
            return getNuEntity().isTrash();
        }
    }

    @Override
    public ObjectType getObjectType() {
        return ObjectType.PI;
    }

    @Override
    public LegacyProfileItem getLegacyEntity() {
        return legacyEntity;
    }

    public void setLegacyEntity(LegacyProfileItem legacyEntity) {
        legacyEntity.setAdapter(this);
        this.legacyEntity = legacyEntity;
    }

    @Override
    public NuProfileItem getNuEntity() {
        return nuEntity;
    }

    public void setNuEntity(NuProfileItem nuEntity) {
        nuEntity.setAdapter(this);
        this.nuEntity = nuEntity;
    }

    public IProfileItemService getItemService() {
        return profileItemService;
    }
}
