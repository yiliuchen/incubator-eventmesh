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

import org.apache.eventmesh.common.protocol.tcp.Command;
import org.apache.eventmesh.common.protocol.tcp.Package;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import client.SubClient;
import client.common.Server;
import client.common.UserAgentUtils;
import client.impl.SubClientImpl;

/**
 * Test Cmd: UNSUBSCRIBE_REQUEST
 */
public class CmdUnSubscribeTest {

    public static Server server = new Server();
    public static SubClient client = new SubClientImpl("127.0.0.1", 10000, UserAgentUtils.createSubUserAgent());

    @BeforeClass
    public static void before_Cmd_unsubscribe() throws Exception {
        server.startAccessServer();
        client.init();
        Thread.sleep(1000);
    }

    @AfterClass
    public static void after_Cmd_unsubscribe() throws Exception {
//        server.shutdownAccessServer();
//        client.close();
    }

    @Test
    public void test_Cmd_unsubscribe() throws Exception {
        Package replyMsg = client.justUnsubscribe("FT0-s-80000000-01-0");
        System.err.println("Reply Message-----------------------------------------" + replyMsg.toString());
        if (replyMsg.getHeader().getCommand() != Command.UNSUBSCRIBE_RESPONSE) {
            Assert.assertTrue("UNSUBSCRIBE_RESPONSE FAIL", false);
        }
    }
}
