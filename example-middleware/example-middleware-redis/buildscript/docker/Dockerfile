FROM centos

MAINTAINER zhou xu

ARG WJ="redis-5.0.5"

ADD http://download.redis.io/releases/$WJ.tar.gz /usr/local/src

RUN tar -zxf /usr/local/src/$WJ.tar.gz -C /usr/local/src/
RUN yum -y install gcc gcc-c++ automake autoconf libtool make
RUN cd /usr/local/src/$WJ \
    && make MALLOC=libc

EXPOSE 6379

WORKDIR /usr/local/src/$WJ

# ./redis-server ../redis.conf
CMD ["redis-server"]
