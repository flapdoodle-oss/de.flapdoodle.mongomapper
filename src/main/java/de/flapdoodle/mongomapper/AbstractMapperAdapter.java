package de.flapdoodle.mongomapper;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.logging.Logger;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Optional;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.DBObject;

import de.flapdoodle.mongomapper.query.Properties;

public abstract class AbstractMapperAdapter<W, R> implements ApplicationStartListener {
    
    private static Logger LOG = LogFactory.logger();

    private final MongoIndexableCollection collection;

    private final ObjectWriteMapper<W> writeMapper;
    private final ObjectReadMapper<R> readMapper;
    private final Optional<ObjectMappingInitializer> initializer;
    private final Optional<ObjectMappingSchemaMigrator> migrator;
    
    private final AtomicBoolean shouldInit = new AtomicBoolean(true);
    private final AtomicBoolean shouldOnAppInit = new AtomicBoolean(true);


    /**
     *
     * Erzeuge eine neue <code>AbstractStore</code> Instanz.
     *
     * @param collection
     *            Collection darf nicht null sein.
     * @param mapper
     *            Mapper darf nicht null sein.
     */
    public <M extends ObjectWriteMapper<W> & ObjectReadMapper<R>> AbstractMapperAdapter(MongoIndexableCollection collection, M mapper) {
        this.collection = Preconditions.checkNotNull(collection, "Collection is null");
        this.writeMapper = Preconditions.checkNotNull(mapper, "mapper is null");
        this.readMapper = Preconditions.checkNotNull(mapper, "mapper is null");

        Optional<ObjectMappingInitializer> optionalInitializer = Optional.absent();
        Optional<ObjectMappingSchemaMigrator> optionalMigrator = Optional.absent();

        if (mapper instanceof ObjectMappingInitializer) {
            ObjectMappingInitializer mappingInit = (ObjectMappingInitializer) mapper;
            optionalInitializer = Optional.of(mappingInit);
        }
        
        if (mapper instanceof ObjectMappingSchemaMigrator) {
            ObjectMappingSchemaMigrator migrator = (ObjectMappingSchemaMigrator) mapper;
            optionalMigrator = Optional.of(migrator);
        }

        initializer = optionalInitializer;
        migrator = optionalMigrator;
    }

    @Override
    public void onApplicationStart() {
        lazyInit();
        if (shouldOnAppInit.getAndSet(false)) {
            onApplicationStartOnce();
        }
    }
    
    protected void onApplicationStartOnce() {
        
    }

    /**
     * Wenn noch nicht geschehen, werden Indizes erstellt.
     */
    @Deprecated
    private void lazyInit() {
        if (shouldInit.getAndSet(false)) {
            if (initializer.isPresent()) {
                ObjectMappingInitializer mappingInit = initializer.get();
                LOG.warning("create index for " + mappingInit);

                for (IndexDefinition index : mappingInit.indexes()) {
                    LOG.warning("create index " + index);
                    collection.ensureIndex(asIndexKeys(index.properties()), asIndexOptions(index));
                }
            }
            if (migrator.isPresent()) {
                ObjectMappingSchemaMigrator schemaMigrator = migrator.get();
                LOG.warning("migrate schema with " + schemaMigrator);
                
                for (SchemaMigration m : schemaMigrator.migrations()) {
                    applySchemaMigration(m);
                }
            }
        }
    }

    protected void applySchemaMigration(SchemaMigration m) {
        collection.apply(m);
    }

    @VisibleForTesting
    protected static DBObject asIndexOptions(IndexDefinition index) {
        BasicDBObjectBuilder builder=new BasicDBObjectBuilder();
        if (index.isUnique()) {
            builder.add("unique", true);
        }
        builder.add("name", name(index));
        return builder.get();
    }

    // http://docs.mongodb.org/manual/reference/limits/#Index-Name-Length
    private static String name(IndexDefinition index) {
        StringBuilder sb=new StringBuilder();
        for (IndexedProperty prop : index.properties()) {
            if (sb.length()>0) {
                sb.append(".");
            }
            sb.append(Properties.name(prop.name()).replace(".", "_"));
        }
        String ret = sb.toString();
        if (ret.length()>127) {
            ret=ret.substring(0,127);
        }
        return ret;
    }

    @VisibleForTesting
    protected static DBObject asIndexKeys(ImmutableList<IndexedProperty> properies) {
        Preconditions.checkArgument(!properies.isEmpty(),"indexList is empty");
        BasicDBObjectBuilder builder = new BasicDBObjectBuilder();
        for (IndexedProperty p : properies) {
            builder.add(Properties.name(p.name()), Sort.asDBObjectValue(p.sort()));
        }
        return builder.get();
    }

    protected ObjectWriteMapper<W> writeMapper() {
        return writeMapper;
    }
    
    protected ObjectReadMapper<R> readMapper() {
        return readMapper;
    }
}
