/*
 * This file is generated by jOOQ.
 */
package com.godov.crudskyweaver.domain.jooq;


import com.godov.crudskyweaver.domain.jooq.tables.Matches;
import org.jooq.Index;
import org.jooq.OrderField;
import org.jooq.impl.DSL;
import org.jooq.impl.Internal;


/**
 * A class modelling indexes of tables in public.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Indexes {

    // -------------------------------------------------------------------------
    // INDEX definitions
    // -------------------------------------------------------------------------

    public static final Index IDX_MATCHES_OPPONENT_ADDRESS = Internal.createIndex(DSL.name("idx_matches_opponent_address"), Matches.MATCHES, new OrderField[] { Matches.MATCHES.OPPONENT_ADDRESS }, false);
}
