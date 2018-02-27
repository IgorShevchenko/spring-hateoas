package com.igor.model;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;

import com.igor.web.MembersController;

/**
 * Centralized place to keep link-building logic, following do not repeat yourself principle.
 */
public class MemberResource extends Resource<Member> {

    public MemberResource(Member member) {
        super(member);

        // Add links to related resources and self
        add(linkTo(methodOn(MembersController.class).getMember(member.getId())).withSelfRel());

        // Add dummy link to member who invited this member
        add(linkTo(methodOn(MembersController.class).getMember(999L)).withRel("invitedBy"));
    }

    public static Resources<MemberResource> toResources(List<Member> members, Link... links) {
        List<MemberResource> resources = members.stream().map(MemberResource::new).collect(Collectors.toList());
        return new Resources<>(resources, links);
    }

}
