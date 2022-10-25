package org.originit.hand.io.impl;

import org.originit.hand.io.Resource;

import java.io.*;

public class SystemFileResource implements Resource {

    private String path;

    public SystemFileResource(String path) {
        this.path = path;
    }

    public SystemFileResource() {
    }

    @Override
    public InputStream getInputStream() throws IOException {
        final File file = new File(path);
        if (!file.exists()) {
            throw new FileNotFoundException(path);
        }
        final FileInputStream fin = new FileInputStream(file);
        return fin;
    }
}
