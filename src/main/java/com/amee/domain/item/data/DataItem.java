package com.amee.domain.item.data;

import com.amee.base.utils.ThreadBeanHolder;
import com.amee.domain.*;
import com.amee.domain.data.DataCategory;
import com.amee.domain.data.ItemDefinition;
import com.amee.domain.item.BaseItem;
import com.amee.domain.item.BaseItemValue;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.joda.time.DateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "DATA_ITEM")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class DataItem extends BaseItem {

    public final static int NAME_MAX_SIZE = 255;
    public final static int PATH_MAX_SIZE = 255;
    public final static int WIKI_DOC_MAX_SIZE = Metadata.VALUE_MAX_SIZE;
    public final static int PROVENANCE_MAX_SIZE = 255;

    @Column(name = "PATH", length = PATH_MAX_SIZE, nullable = false)
    private String path = "";

    /**
     * A temporary and transient object for use in validation. See the getValues() method below.
     */
    @Transient
    private Object values;

    /**
     * A temporary and transient object for use in validation. See the getUnits() method below.
     */
    @Transient
    private Object units;

    /**
     * A temporary and transient object for use in validation. See the getPerUnits() method below.
     * This bean has the same structure as the units bean.
     */
    @Transient
    private Object perUnits;

    public DataItem() {
        super();
    }

    public DataItem(DataCategory dataCategory, ItemDefinition itemDefinition) {
        super(dataCategory, itemDefinition);
    }

    protected void copyTo(DataItem o) {
        super.copyTo(o);
        o.path = path;
    }

    @Override
    public boolean isTrash() {
        return status.equals(AMEEStatus.TRASH) || getDataCategory().isTrash() || getItemDefinition().isTrash();
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        if (path == null) {
            path = "";
        }
        this.path = path;
    }

    public String getWikiDoc() {
        return getMetadataValue("wikiDoc");
    }

    public void setWikiDoc(String wikiDoc) {
        getOrCreateMetadata("wikiDoc").setValue(wikiDoc);
        onModify();
    }

    public String getProvenance() {
        return getMetadataValue("provenance");
    }

    public void setProvenance(String provenance) {
        getOrCreateMetadata("provenance").setValue(provenance);
        onModify();
    }

    @Override
    public ObjectType getObjectType() {
        return ObjectType.DI;
    }

    /**
     * Returns a temporary and transient JavaBean related to the Item Values associated with this
     * DataItem. The bean is intended as a target for property binding during input validation within
     * PUT and POST requests. See {@link ItemDefinition#getDataItemValuesBean()} for more details
     * on how this bean is created.
     *
     * @return
     */
    public Object getValues() {
        if (values == null) {
            values = getItemDefinition().getDataItemValuesBean();
        }
        return values;
    }

    /**
     * Returns a temporary and transient JavaBean related to the units associated with this
     * ProfileItem. The bean is intended as a target for property binding during input validation within
     * PUT and POST requests. See {@link ItemDefinition#getDataItemUnitsBean()} for more details
     * on how this bean is created.
     *
     * @return
     */
    public Object getUnits() {
        if (units == null) {
            units = getItemDefinition().getDataItemUnitsBean();
        }
        return units;
    }

    /**
     * Returns a temporary and transient JavaBean related to the perUnits associated with this
     * ProfileItem. The bean is intended as a target for property binding during input validation within
     * PUT and POST requests. See {@link ItemDefinition#getDataItemUnitsBean()} for more details
     * on how this bean is created.
     *
     * @return
     */
    public Object getPerUnits() {
        if (perUnits == null) {
            perUnits = getItemDefinition().getDataItemUnitsBean();
        }
        return perUnits;
    }

    /**
     * Simulates the legacy DataItem.getLabel method. Usage of this is discouraged.
     * <p/>
     * This is used in the following algorithms:
     *  - 599C0F18A362
     *  - 7A613C522477
     *
     * @return the DataItem label.
     */
    @Deprecated
    @Transient
    public String getLabel() {
        return getDataItemService().getLabel(this);
    }

    /**
     * Simulates the legacy DataItem.getItemValues method. Usage of this is discouraged.
     * <p/>
     * This is used in algorithms.
     *
     * @return a list of {@link BaseItemValue}s
     */
    @Deprecated
    @Transient
    public List<BaseItemValue> getItemValues() {
        return getDataItemService().getItemValues(this);
    }

    /**
     * Gets the DataItemService bound to the current thread. Usage of this is discouraged.
     *
     * @return the current {@link com.amee.domain.DataItemService}
     */
    @Deprecated
    @Transient
    private DataItemService getDataItemService() {
        return ThreadBeanHolder.get(DataItemService.class);
    }

    public List<IAMEEEntityReference> getHierarchy() {
        List<IAMEEEntityReference> entities = new ArrayList<IAMEEEntityReference>();
        entities.add(this);
        DataCategory dc = getDataCategory();
        while (dc != null) {
            entities.add(dc);
            dc = dc.getDataCategory();
        }
        Collections.reverse(entities);
        return entities;
    }
}