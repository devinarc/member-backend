package com.member.members.MemberResources;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.JSONPObject;
import com.member.members.model.Member;
import com.member.members.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jackson.JsonObjectSerializer;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value="/api")
public class MemberResources {
    @Autowired
    private MemberService memberService;

    @GetMapping(value = "/members/{memberId}")
    public Member getMember (@PathVariable("memberId") int memberId){
        Member selectedCust = memberService.getMember(memberId);
        return selectedCust;
    }

    @GetMapping(value = "/members/update/{memberId}")
    public Member updateMember(@PathVariable("memberId") int memberId, @RequestBody Member member){
        return memberService.updateMemberDetails(memberId, member);
    }

    @PostMapping(value="/members/save")
    public Member saveMember(@RequestBody Member member){
        memberService.addMember(member);
        return member;
    }

    @GetMapping(value="/members/all")
    public List<Member> getAllMembers(Model model){
        return memberService.getAllMembers();
    }

    @PostMapping(value = "/members/login")
    public String getMember(@RequestBody String json ){
        ObjectMapper mapper = new ObjectMapper();
        HashMap<String, Object> map = new HashMap<String, Object>();
        String result = "";

        try{
             map = mapper.readValue(json, new TypeReference<Map<String, Object>>(){});
             result = memberService.getSelectedMember(map.get("username").toString(), map.get("password").toString());

            //Print JSON output
            System.out.println(map);

        }  catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //return memberService.getSelectedMember(username, password);
        return result;
    }
}
