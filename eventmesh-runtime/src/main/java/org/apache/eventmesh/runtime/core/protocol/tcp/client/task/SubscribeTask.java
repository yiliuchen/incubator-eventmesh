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

package org.apache.eventmesh.runtime.core.protocol.tcp.client.task;

import java.util.ArrayList;
import java.util.List;

import io.netty.channel.ChannelHandlerContext;

import org.apache.eventmesh.common.protocol.tcp.Command;
import org.apache.eventmesh.common.protocol.tcp.Header;
import org.apache.eventmesh.common.protocol.tcp.OPStatus;
import org.apache.eventmesh.common.protocol.tcp.Package;
import org.apache.eventmesh.common.protocol.tcp.Subscription;
import org.apache.eventmesh.runtime.boot.EventMeshTCPServer;
import org.apache.eventmesh.runtime.util.EventMeshUtil;
import org.apache.eventmesh.runtime.util.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SubscribeTask extends AbstractTask {

    private final Logger messageLogger = LoggerFactory.getLogger("message");

    public SubscribeTask(Package pkg, ChannelHandlerContext ctx, long startTime, EventMeshTCPServer eventMeshTCPServer) {
        super(pkg, ctx, startTime, eventMeshTCPServer);
    }

    @Override
    public void run() {
        long taskExecuteTime = System.currentTimeMillis();
        Package msg = new Package();
        try {
            Subscription subscriptionInfo = (Subscription) pkg.getBody();
            if (subscriptionInfo == null) {
                throw new Exception("subscriptionInfo is null");
            }

            List<String> topicList = new ArrayList<>();
            for (int i = 0; i < subscriptionInfo.getTopicList().size(); i++) {
                String topic = subscriptionInfo.getTopicList().get(i);
                if (!EventMeshUtil.isValidRMBTopic(topic)) {
                    throw new Exception("invalid topic!");
                }
                topicList.add(topic);
            }
            synchronized (session) {
                session.subscribe(topicList);
                messageLogger.info("SubscribeTask succeed|user={}|topics={}", session.getClient(), topicList);
            }
            msg.setHeader(new Header(Command.SUBSCRIBE_RESPONSE, OPStatus.SUCCESS.getCode(), OPStatus.SUCCESS.getDesc(), pkg.getHeader()
                    .getSeq()));
        } catch (Exception e) {
            messageLogger.error("SubscribeTask failed|user={}|errMsg={}", session.getClient(), e);
            msg.setHeader(new Header(Command.SUBSCRIBE_RESPONSE, OPStatus.FAIL.getCode(), e.toString(), pkg.getHeader()
                    .getSeq()));
        } finally {
            Utils.writeAndFlush(msg, startTime, taskExecuteTime, session.getContext(), session);
        }
    }


}
