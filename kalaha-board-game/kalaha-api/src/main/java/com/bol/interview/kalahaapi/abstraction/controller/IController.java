package com.bol.interview.kalahaapi.abstraction.controller;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Root controller class for all controllers
 */
public interface IController {
    default ObjectMapper getJsonMapper() {
        return new ObjectMapper();
    }
}
