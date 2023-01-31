/*
 * This file is generated by jOOQ.
 */
package com.godov.crudskyweaver.domain.jooq.tables;


import com.godov.crudskyweaver.config.jooq.binding.PostgresJsonbToAdditionalInfoBinding;
import com.godov.crudskyweaver.domain.jooq.Keys;
import com.godov.crudskyweaver.domain.jooq.Public;
import com.godov.crudskyweaver.domain.jooq.tables.records.UsersRecord;
import com.godov.crudskyweaver.dto.user.AdditionalInfo;
import org.jooq.Record;
import org.jooq.*;
import org.jooq.impl.DSL;
import org.jooq.impl.SQLDataType;
import org.jooq.impl.TableImpl;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.function.Function;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Users extends TableImpl<UsersRecord> {

    private static final long serialVersionUID = 1L;

    /**
     * The reference instance of <code>public.users</code>
     */
    public static final Users USERS = new Users();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<UsersRecord> getRecordType() {
        return UsersRecord.class;
    }

    /**
     * The column <code>public.users.id</code>.
     */
    public final TableField<UsersRecord, UUID> ID = createField(DSL.name("id"), SQLDataType.UUID.nullable(false).defaultValue(DSL.field("gen_random_uuid()", SQLDataType.UUID)), this, "");

    /**
     * The column <code>public.users.nickname</code>.
     */
    public final TableField<UsersRecord, String> NICKNAME = createField(DSL.name("nickname"), SQLDataType.VARCHAR(255).nullable(false), this, "");

    /**
     * The column <code>public.users.registration_date</code>.
     */
    public final TableField<UsersRecord, LocalDate> REGISTRATION_DATE = createField(DSL.name("registration_date"), SQLDataType.LOCALDATE.nullable(false).defaultValue(DSL.field("CURRENT_DATE", SQLDataType.LOCALDATE)), this, "");

    /**
     * The column <code>public.users.email</code>.
     */
    public final TableField<UsersRecord, String> EMAIL = createField(DSL.name("email"), SQLDataType.VARCHAR(255).nullable(false), this, "");

    /**
     * The column <code>public.users.additional_info</code>.
     */
    public final TableField<UsersRecord, AdditionalInfo> ADDITIONAL_INFO = createField(DSL.name("additional_info"), SQLDataType.JSONB, this, "", new PostgresJsonbToAdditionalInfoBinding());

    private Users(Name alias, Table<UsersRecord> aliased) {
        this(alias, aliased, null);
    }

    private Users(Name alias, Table<UsersRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment(""), TableOptions.table());
    }

    /**
     * Create an aliased <code>public.users</code> table reference
     */
    public Users(String alias) {
        this(DSL.name(alias), USERS);
    }

    /**
     * Create an aliased <code>public.users</code> table reference
     */
    public Users(Name alias) {
        this(alias, USERS);
    }

    /**
     * Create a <code>public.users</code> table reference
     */
    public Users() {
        this(DSL.name("users"), null);
    }

    public <O extends Record> Users(Table<O> child, ForeignKey<O, UsersRecord> key) {
        super(child, key, USERS);
    }

    @Override
    public Schema getSchema() {
        return aliased() ? null : Public.PUBLIC;
    }

    @Override
    public UniqueKey<UsersRecord> getPrimaryKey() {
        return Keys.PK_USERS_ID;
    }

    @Override
    public List<UniqueKey<UsersRecord>> getUniqueKeys() {
        return Arrays.asList(Keys.UNIQUE_USERS_NICKNAME, Keys.UNIQUE_USERS_EMAIL);
    }

    @Override
    public Users as(String alias) {
        return new Users(DSL.name(alias), this);
    }

    @Override
    public Users as(Name alias) {
        return new Users(alias, this);
    }

    @Override
    public Users as(Table<?> alias) {
        return new Users(alias.getQualifiedName(), this);
    }

    /**
     * Rename this table
     */
    @Override
    public Users rename(String name) {
        return new Users(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public Users rename(Name name) {
        return new Users(name, null);
    }

    /**
     * Rename this table
     */
    @Override
    public Users rename(Table<?> name) {
        return new Users(name.getQualifiedName(), null);
    }

    // -------------------------------------------------------------------------
    // Row5 type methods
    // -------------------------------------------------------------------------

    @Override
    public Row5<UUID, String, LocalDate, String, AdditionalInfo> fieldsRow() {
        return (Row5) super.fieldsRow();
    }

    /**
     * Convenience mapping calling {@link SelectField#convertFrom(Function)}.
     */
    public <U> SelectField<U> mapping(Function5<? super UUID, ? super String, ? super LocalDate, ? super String, ? super AdditionalInfo, ? extends U> from) {
        return convertFrom(Records.mapping(from));
    }

    /**
     * Convenience mapping calling {@link SelectField#convertFrom(Class,
     * Function)}.
     */
    public <U> SelectField<U> mapping(Class<U> toType, Function5<? super UUID, ? super String, ? super LocalDate, ? super String, ? super AdditionalInfo, ? extends U> from) {
        return convertFrom(toType, Records.mapping(from));
    }
}