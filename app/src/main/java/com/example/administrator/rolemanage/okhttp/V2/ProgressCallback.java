package com.example.administrator.rolemanage.okhttp.V2;

interface ProgressCallback {
    void updateProgress(int progress, long networkSpeed, boolean done);
}
