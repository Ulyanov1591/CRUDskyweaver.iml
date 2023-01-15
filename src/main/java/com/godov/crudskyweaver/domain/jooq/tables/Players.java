/*
 * This file is generated by jOOQ.
 */
package com.godov.crudskyweaver.domain.jooq.tables;


import com.godov.crudskyweaver.domain.jooq.Keys;
import com.godov.crudskyweaver.domain.jooq.Public;
import com.godov.crudskyweaver.domain.jooq.tables.records.PlayersRecord;
import org.jooq.Record;
import org.jooq.*;
import org.jooq.impl.DSL;
import org.jooq.impl.Internal;
import org.jooq.impl.SQLDataType;
import org.jooq.impl.TableImpl;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Players extends TableImpl<PlayersRecord> {

    private static final long serialVersionUID = 1L;

    /**
     * The reference instance of <code>public.players</code>
     */
    public static final Players PLAYERS = new Players();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<PlayersRecord> getRecordType() {
        return PlayersRecord.class;
    }

    /**
     * The column <code>public.players.polygon_address</code>.
     */
    public final TableField<PlayersRecord, String> POLYGON_ADDRESS = createField(DSL.name("polygon_address"), SQLDataType.VARCHAR(42).nullable(false), this, "");

    /**
     * The column <code>public.players.nickname</code>.
     */
    public final TableField<PlayersRecord, String> NICKNAME = createField(DSL.name("nickname"), SQLDataType.VARCHAR(255).nullable(false), this, "");

    /**
     * The column <code>public.players.joined_on</code>.
     */
    public final TableField<PlayersRecord, LocalDate> JOINED_ON = createField(DSL.name("joined_on"), SQLDataType.LOCALDATE.nullable(false), this, "");

    private Players(Name alias, Table<PlayersRecord> aliased) {
        this(alias, aliased, null);
    }

    private Players(Name alias, Table<PlayersRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment(""), TableOptions.table());
    }

    /**
     * Create an aliased <code>public.players</code> table reference
     */
    public Players(String alias) {
        this(DSL.name(alias), PLAYERS);
    }

    /**
     * Create an aliased <code>public.players</code> table reference
     */
    public Players(Name alias) {
        this(alias, PLAYERS);
    }

    /**
     * Create a <code>public.players</code> table reference
     */
    public Players() {
        this(DSL.name("players"), null);
    }

    public <O extends Record> Players(Table<O> child, ForeignKey<O, PlayersRecord> key) {
        super(child, key, PLAYERS);
    }

    @Override
    public Schema getSchema() {
        return aliased() ? null : Public.PUBLIC;
    }

    @Override
    public UniqueKey<PlayersRecord> getPrimaryKey() {
        return Keys.PK_PLAYERS_ADDRESS;
    }

    @Override
    public List<UniqueKey<PlayersRecord>> getUniqueKeys() {
        return Arrays.asList(Keys.UNIQUE_NICKNAME);
    }

    @Override
    public List<Check<PlayersRecord>> getChecks() {
        return Arrays.asList(
            Internal.createCheck(this, DSL.name("polygon_address_check"), "((((polygon_address)::text ~~ '0x%'::text) AND (char_length((polygon_address)::text) = 42)))", true)
        );
    }

    @Override
    public Players as(String alias) {
        return new Players(DSL.name(alias), this);
    }

    @Override
    public Players as(Name alias) {
        return new Players(alias, this);
    }

    @Override
    public Players as(Table<?> alias) {
        return new Players(alias.getQualifiedName(), this);
    }

    /**
     * Rename this table
     */
    @Override
    public Players rename(String name) {
        return new Players(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public Players rename(Name name) {
        return new Players(name, null);
    }

    /**
     * Rename this table
     */
    @Override
    public Players rename(Table<?> name) {
        return new Players(name.getQualifiedName(), null);
    }

    // -------------------------------------------------------------------------
    // Row3 type methods
    // -------------------------------------------------------------------------

    @Override
    public Row3<String, String, LocalDate> fieldsRow() {
        return (Row3) super.fieldsRow();
    }

    /**
     * Convenience mapping calling {@link SelectField#convertFrom(Function)}.
     */
    public <U> SelectField<U> mapping(Function3<? super String, ? super String, ? super LocalDate, ? extends U> from) {
        return convertFrom(Records.mapping(from));
    }

    /**
     * Convenience mapping calling {@link SelectField#convertFrom(Class,
     * Function)}.
     */
    public <U> SelectField<U> mapping(Class<U> toType, Function3<? super String, ? super String, ? super LocalDate, ? extends U> from) {
        return convertFrom(toType, Records.mapping(from));
    }
}