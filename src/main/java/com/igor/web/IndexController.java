package com.igor.web;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

import org.springframework.hateoas.ResourceSupport;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
public class IndexController {

    @GetMapping()
    public ResourceSupport index() {
        ResourceSupport index = new ResourceSupport();
        index.add(linkTo(MembersController.class).withRel("members"));
        index.add(linkTo(getClass()).withSelfRel());
        return index;
    }

}
