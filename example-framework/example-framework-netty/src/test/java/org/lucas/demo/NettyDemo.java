package org.lucas.demo;

import java.nio.channels.SelectionKey;

public class NettyDemo {

    private void processSelectedKeysOptimized() {
        //3 轮询处理所有套接字的读写事件
        for (int i = 0; i < selectedKeys.size; ++i) {
            final SelectionKey k = selectedKeys.keys[i];
            selectedKeys.keys[i] = null;
            final Object a = k.attachment();
            // 如果是 AbstractNioChannel 子类实例
            if (a instanceof AbstractNioChannel) {
                // 处理每个 NioSocketChannel 的读写事件
                processSelectedKey(k, (AbstractNioChannel) a);
            } else {
                @SuppressWarnings("unchecked")
                NioTask<SelectableChannel> task = (NioTask<SelectableChannel>) a;
                processSelectedKey(k, task);
            }
            if (needsToSelectAgain) {
                selectedKeys.reset(i + 1);
                selectAgain();
                i = -1;
            }
        }
    }

    private void processSelectedKey(SelectionKey k, AbstractNioChannel ch) {
        final AbstractNioChannel.NioUnsafe unsafe = ch.unsafe();
                ...
                //AbstractNioByteChannel 的 read 方法
                if ((readyOps & (SelectionKey.OP_ READ | SelectionKey.OP_ ACCEPT)) !=0
                    || readyOps == 0){
                    unsafe.read();
                }
            }
        } catch(CancelledKeyException ignored){
            unsafe.close(unsafe.voidPromise());
        }
    }
