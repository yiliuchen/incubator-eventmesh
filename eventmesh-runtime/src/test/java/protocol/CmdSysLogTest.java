/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package protocol;

import io.netty.channel.ChannelHandlerContext;

import org.apache.eventmesh.common.protocol.tcp.Package;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import client.SubClient;
import client.common.Server;
import client.common.UserAgentUtils;
import client.hook.ReceiveMsgHook;
import client.impl.SubClientImpl;

/**
 * Test Cmd: SYS_LOG_TO_LOGSERVER
 */
public class CmdSysLogTest {
    public static Server server = new Server();
    public static SubClient client = new SubClientImpl("127.0.0.1", 10000, UserAgentUtils.createSubUserAgent());

    @BeforeClass
    public static void before_Cmd_syslog() throws Exception {
        server.startAccessServer();
        client.init();
    }

    @AfterClass
    public static void after_Cmd_syslog() throws Exception {
//        server.shutdownAccessServer();
//        client.close();
    }

    @Test
    public void test_Cmd_syslog() throws Exception {
        client.registerBusiHandler(new ReceiveMsgHook() {
            @Override
            public void handle(Package msg, ChannelHandlerContext ctx) {
                System.err.println("receive response from server:------------------" + msg.toString());
                Assert.assertTrue("receive a error command", false);
            }
        });
//        client.sysLog();
        Thread.sleep(1000);
    }
}
