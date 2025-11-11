package io.github.wj9806.mini.redis.server.handler;

import io.github.wj9806.mini.redis.command.Command;
import io.github.wj9806.mini.redis.command.CommandType;
import io.github.wj9806.mini.redis.protocal.BulkString;
import io.github.wj9806.mini.redis.protocal.Errors;
import io.github.wj9806.mini.redis.protocal.Resp;
import io.github.wj9806.mini.redis.protocal.RespArray;
import io.github.wj9806.mini.redis.server.core.RedisCore;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class RespCommandHandler extends SimpleChannelInboundHandler<Resp> {

    private final RedisCore redisCore;

    public RespCommandHandler(RedisCore redisCore) {
        this.redisCore = redisCore;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Resp msg) throws Exception {
        if (msg instanceof RespArray respArray) {
            Resp response = processCommand(respArray);
            if (response != null) {
                ctx.channel().writeAndFlush(response);
            }
        } else {
            ctx.channel().writeAndFlush(new Errors("not support command"));
        }
    }

    private Resp processCommand(RespArray respArray) {
        if (respArray.getContent().length == 0) {
            return new Errors("command is empty");
        }

        try {
            Resp[] array = respArray.getContent();
            String commandName = new String(((BulkString) array[0]).getContent().getBytes());
            commandName = commandName.toUpperCase();

            CommandType commandType = null;
            try {
                commandType = CommandType.valueOf(commandName);
            } catch (IllegalArgumentException e) {
                return new Errors(commandName + " command not found");
            }
            Command command = commandType.getSupplier().apply(redisCore);
            command.setContext(array);
            return command.handle();
        } catch (Exception e) {
            return new Errors("process command error");
        }
    }
}
