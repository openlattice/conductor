package com.kryptnostic.conductor.pods;

import com.datastax.driver.core.Cluster;
import com.datastax.spark.connector.cql.CassandraConnectionFactory;
import com.datastax.spark.connector.cql.CassandraConnectorConf;
import com.google.common.base.Preconditions;
import com.kryptnostic.rhizome.pods.SparkPod;

import scala.collection.immutable.Set;

public class LoomCassandraConnectionFactory implements CassandraConnectionFactory {
    private static final long serialVersionUID = 4167984603461650295L;

    static {
        SparkPod.CASSANDRA_CONNECTION_FACTORY_CLASS = LoomCassandraConnectionFactory.class.getCanonicalName();
    }

    @Override
    public Cluster createCluster( CassandraConnectorConf ccf ) {
        return Preconditions.checkNotNull( SparkPod.getCluster(),
                "This is a hack that must be initialized before being used in a session using the CassandraPod" );
    }

    @SuppressWarnings( "unchecked" )
    @Override
    public Set<String> properties() {
        return (Set<String>) scala.collection.immutable.Set$.MODULE$.empty();
    }

}
