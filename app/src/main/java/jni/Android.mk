LOCAL_PATH := $(call my-dir)

include $(CLEAR_VARS)

LOCAL_MODULE    := FsTools
LOCAL_SRC_FILES := FsTools.c

include $(BUILD_SHARED_LIBRARY)