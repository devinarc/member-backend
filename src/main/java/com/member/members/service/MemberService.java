package com.member.members.service;

import com.member.members.dao.MemberDAO;
import com.member.members.model.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.CopyOnWriteArrayList;

@Component
public class MemberService {

    @Autowired
    private MemberDAO memberDAO;

    private List<Member> memberList =  new CopyOnWriteArrayList<>();

    public Member addMember(Member member){
        memberDAO.save(member);
        return member;
    }

    public List<Member> getAllMembers(){
        return memberDAO.findAll();
    }

    public Member getMember(int memberId){
        memberList = this.getAllMembers();
        return memberList.stream().filter(m->m.getId() == memberId).findFirst().get();
    }

    public Member save(Member member){
        memberDAO.save(member);
        return member;
    }

    public String getSelectedMember(String username, String password ){

        List<Member> x = this.getAllMembers();
        try{
            Member reqMember =  x.stream().filter(m->m.getUsername().equals(username)).findFirst().get();
            if(password.equals(reqMember.getPassword()) == false){
                return "Invalid Username/Password";
            }else{
                return "Login Successful";
            }
        }catch(NoSuchElementException e){
            return "Invalid Username/Password";
        }



    }

    public String getMemberName(String username){
        List<Member>x = this.getAllMembers();
        try{
            Member reqMember =  x.stream().filter(m->m.getUsername().equals(username)).findFirst().get();
            return reqMember.getName();
        }catch(NoSuchElementException e){
            return null;
        }
    }


    public Member updateMemberDetails(int memberId, Member member){
        Member reqMember = this.getMember(memberId);
        System.out.println(reqMember.getId());
        String updateMemberName = member.getName();
        String updateMemberEmail  = member.getEmail();
        String updateMemberPassword = member.getPassword();
        String updateMemberUsername = member.getUsername();
        if(reqMember.getId() != memberId){
            return null;
        }
        if(updateMemberName!=null){
            reqMember.setName(updateMemberName);
        }
        if(updateMemberEmail!=null){
            reqMember.setEmail(updateMemberEmail);
        }
        if(updateMemberPassword!=null){
            reqMember.setPassword(updateMemberPassword);
        }
        if(updateMemberUsername!=null){
            reqMember.setUsername(updateMemberUsername);
        }
        this.save(reqMember);
        return reqMember;

    }



}
