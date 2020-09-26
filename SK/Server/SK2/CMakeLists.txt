cmake_minimum_required(VERSION 3.15)
project(SK2)

set(CMAKE_CXX_STANDARD 14)
set(CMAKE_CXX_FLAGS "${CMAKE_CXX_FLAGS} -Wall -std=c++14 -pthread")
include_directories(.)

add_executable(SK2 main.cpp User.h)