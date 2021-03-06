# AOP

[TOC]

## 目录

- [角色](#1-角色)
- [静态AOP](#2-静态AOP)
- [动态AOP](#3-动态AOP)
  - [动态代理](#31-动态代理)
  - [自定义类加载器](#32-自定义类加载器)
  - [字节码转换](#33-字节码转换)

## 1. 角色
1. 被代理的类
2. 被代理类的接口
3. 织入器
4. 切面(InvocationHandler)

## 2. 静态AOP
原理:在编译期，切面直接以字节码形式编译到目标字节码文件中

## 3. 动态AOP

### 3.1 动态代理
原理:在运行期，目标类加载后，为接口动态生成代理类。将切面织入到代理类中

### 3.2 自定义类加载器
原理:在运行期，目标加载前，将切面逻辑加到目标字节码里.

### 3.3 字节码转换
原理:在运行期，所有类加载器加载字节码前进行拦截.
