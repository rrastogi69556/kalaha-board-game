package com.bol.interview.kalahaapi.common.utils;

import org.apache.commons.io.FileUtils;
import org.springframework.util.ResourceUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * A generic utility operations.
 */
public class CommonUtils {
    private CommonUtils(){}

    public static String readFileToString(String path) throws IOException {
        return FileUtils.readFileToString(ResourceUtils.getFile(path), StandardCharsets.UTF_8);
    }

}
