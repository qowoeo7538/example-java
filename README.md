# docker
## 1.1 创建容器 

docker run [OPTIONS] IMAGE [COMMAND] [ARG...]

- -i 保持stdin打开
- -t: 分配一个伪终端(tty)
- -d: 后台运行
- --name string : 容器别名
- -p:端口绑定
  - -p ip:本地端口:容器端口
  - -p 本地端口:容器端口
- -v 本地路径地址:容器路径


## 1.2 进入容器

docker exec [OPTIONS] CONTAINER COMMAND [ARG...]

- -i 保持stdin打开
- -t: 分配一个伪终端(tty)
- -d: 后台运行