/**
 * Created by Concept People Consulting 2014
 */
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package de.flapdoodle.mongomapper;


import org.junit.After;
import org.junit.Before;

import com.mongodb.Mongo;
import com.mongodb.MongoClient;

import de.flapdoodle.embed.mongo.Command;
import de.flapdoodle.embed.mongo.MongodExecutable;
import de.flapdoodle.embed.mongo.MongodProcess;
import de.flapdoodle.embed.mongo.MongodStarter;
import de.flapdoodle.embed.mongo.config.ArtifactStoreBuilder;
import de.flapdoodle.embed.mongo.config.MongoCmdOptionsBuilder;
import de.flapdoodle.embed.mongo.config.MongodConfigBuilder;
import de.flapdoodle.embed.mongo.config.Net;
import de.flapdoodle.embed.mongo.config.RuntimeConfigBuilder;
import de.flapdoodle.embed.mongo.distribution.Version;
import de.flapdoodle.embed.process.config.IRuntimeConfig;
import de.flapdoodle.embed.process.distribution.Platform;
import de.flapdoodle.embed.process.extract.ITempNaming;
import de.flapdoodle.embed.process.extract.UUIDTempNaming;
import de.flapdoodle.embed.process.extract.UserTempNaming;
import de.flapdoodle.embed.process.runtime.Network;

/**
 *
 * @author mosmann
 */
public abstract class AbstractMongoDBTest {

    private static final IRuntimeConfig runtimeConfig = runtimeConfig();

    private MongodExecutable _mongodExe;
    private MongodProcess _mongod;

    private MongoClient _mongo;

    @Before
    public void setUp() throws Exception {

        MongodStarter runtime = MongodStarter.getInstance(runtimeConfig);
        _mongodExe = runtime.prepare(new MongodConfigBuilder().version(Version.Main.V2_6)
                .cmdOptions(new MongoCmdOptionsBuilder()
                .verbose(true)
                .build())
                .net(new Net(12345, Network.localhostIsIPv6())).build());
        _mongod = _mongodExe.start();

        _mongo = new MongoClient("localhost", 12345);
    }

    private static IRuntimeConfig runtimeConfig() {
        ITempNaming executableTempNaming = new UUIDTempNaming();
        if ((Platform.detect() == Platform.Windows)) {
            executableTempNaming = new UserTempNaming();
        }

        IRuntimeConfig runtimeConfig = new RuntimeConfigBuilder()
                .defaults(Command.MongoD)
                .artifactStore(
                        new ArtifactStoreBuilder().defaults(Command.MongoD)
                                .executableNaming(executableTempNaming).build()).build();
        return runtimeConfig;
    }

    @After
    public void tearDown() throws Exception {
        _mongod.stop();
        _mongodExe.stop();
    }

    public Mongo getMongo() {
        return _mongo;
    }
}
