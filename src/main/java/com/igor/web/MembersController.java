package com.igor.web;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resources;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.igor.model.Member;
import com.igor.model.MemberResource;
import com.igor.repository.MemberRestRepository;
import com.igor.setup.NoSuchMemberException;

@RestController
@RequestMapping(value = "/members", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
public class MembersController {

    private final MemberRestRepository repository;

    @Autowired
    public MembersController(MemberRestRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Resources<MemberResource> getMembers() {
        Pageable pageable = new PageRequest(0, 100);
        Page<Member> page = this.repository.findAll(pageable);
        List<Member> members = page.getContent();

        // Add dummy link to next page
        Link nextPageRel = linkTo(methodOn(getClass()).getMembers()).withRel("next-page");
        Link selfRel = linkTo(methodOn(getClass()).getMembers()).withSelfRel();
        return MemberResource.toResources(members, selfRel, nextPageRel);
    }

    @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    @ResponseStatus(HttpStatus.CREATED)
    public HttpHeaders createMember(@RequestBody Member member) {

        member = this.repository.save(member);

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(linkTo(methodOn(getClass()).getMember(member.getId())).toUri());
        return headers;
    }

    @GetMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public MemberResource getMember(@PathVariable long id) {

        Member member = this.repository.findOne(id);
        if (member == null) {
            throw new NoSuchMemberException(id);
        }

        return new MemberResource(member);
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteMember(@PathVariable long id) {

        Member member = this.repository.findOne(id);
        if (member == null) {
            throw new NoSuchMemberException(id);
        }

        this.repository.delete(id);
    }

    @PutMapping(value = "/{id}", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    @ResponseStatus(HttpStatus.OK)
    public void updateMember(@PathVariable long id, @RequestBody Member member) {

        Member memberDb = this.repository.findOne(id);
        if (memberDb == null) {
            throw new NoSuchMemberException(id);
        }

        memberDb.setFirstName(member.getFirstName());
        memberDb.setLastName(member.getLastName());
        memberDb.setZipcode(member.getZipcode());
        memberDb.setDateOfBirth(member.getDateOfBirth());
        this.repository.save(memberDb);
    }

    @GetMapping(value = "/search")
    @ResponseStatus(HttpStatus.OK)
    public Resources<MemberResource> searchMember(@RequestParam(value = "lastname") String lastName) {

        List<Member> members = this.repository.findByLastName(lastName);

        Link selfRel = linkTo(methodOn(getClass()).searchMember(lastName)).withSelfRel();
        return MemberResource.toResources(members, selfRel);
    }

}
