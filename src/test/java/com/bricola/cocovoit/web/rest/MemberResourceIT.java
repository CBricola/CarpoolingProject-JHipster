package com.bricola.cocovoit.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.bricola.cocovoit.IntegrationTest;
import com.bricola.cocovoit.domain.Member;
import com.bricola.cocovoit.repository.MemberRepository;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link MemberResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class MemberResourceIT {

    private static final String DEFAULT_FIRST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_FIRST_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_LAST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_LAST_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_PROFESSIONAL_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_PROFESSIONAL_EMAIL = "BBBBBBBBBB";

    private static final String DEFAULT_PHONE = "AAAAAAAAAA";
    private static final String UPDATED_PHONE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/members";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restMemberMockMvc;

    private Member member;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Member createEntity(EntityManager em) {
        Member member = new Member()
            .firstName(DEFAULT_FIRST_NAME)
            .lastName(DEFAULT_LAST_NAME)
            .professionalEmail(DEFAULT_PROFESSIONAL_EMAIL)
            .phone(DEFAULT_PHONE);
        return member;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Member createUpdatedEntity(EntityManager em) {
        Member member = new Member()
            .firstName(UPDATED_FIRST_NAME)
            .lastName(UPDATED_LAST_NAME)
            .professionalEmail(UPDATED_PROFESSIONAL_EMAIL)
            .phone(UPDATED_PHONE);
        return member;
    }

    @BeforeEach
    public void initTest() {
        member = createEntity(em);
    }

    @Test
    @Transactional
    void createMember() throws Exception {
        int databaseSizeBeforeCreate = memberRepository.findAll().size();
        // Create the Member
        restMemberMockMvc
            .perform(
                post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(member))
            )
            .andExpect(status().isCreated());

        // Validate the Member in the database
        List<Member> memberList = memberRepository.findAll();
        assertThat(memberList).hasSize(databaseSizeBeforeCreate + 1);
        Member testMember = memberList.get(memberList.size() - 1);
        assertThat(testMember.getFirstName()).isEqualTo(DEFAULT_FIRST_NAME);
        assertThat(testMember.getLastName()).isEqualTo(DEFAULT_LAST_NAME);
        assertThat(testMember.getProfessionalEmail()).isEqualTo(DEFAULT_PROFESSIONAL_EMAIL);
        assertThat(testMember.getPhone()).isEqualTo(DEFAULT_PHONE);
    }

    @Test
    @Transactional
    void createMemberWithExistingId() throws Exception {
        // Create the Member with an existing ID
        member.setId(1L);

        int databaseSizeBeforeCreate = memberRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restMemberMockMvc
            .perform(
                post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(member))
            )
            .andExpect(status().isBadRequest());

        // Validate the Member in the database
        List<Member> memberList = memberRepository.findAll();
        assertThat(memberList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkFirstNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = memberRepository.findAll().size();
        // set the field null
        member.setFirstName(null);

        // Create the Member, which fails.

        restMemberMockMvc
            .perform(
                post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(member))
            )
            .andExpect(status().isBadRequest());

        List<Member> memberList = memberRepository.findAll();
        assertThat(memberList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkLastNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = memberRepository.findAll().size();
        // set the field null
        member.setLastName(null);

        // Create the Member, which fails.

        restMemberMockMvc
            .perform(
                post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(member))
            )
            .andExpect(status().isBadRequest());

        List<Member> memberList = memberRepository.findAll();
        assertThat(memberList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkProfessionalEmailIsRequired() throws Exception {
        int databaseSizeBeforeTest = memberRepository.findAll().size();
        // set the field null
        member.setProfessionalEmail(null);

        // Create the Member, which fails.

        restMemberMockMvc
            .perform(
                post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(member))
            )
            .andExpect(status().isBadRequest());

        List<Member> memberList = memberRepository.findAll();
        assertThat(memberList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkPhoneIsRequired() throws Exception {
        int databaseSizeBeforeTest = memberRepository.findAll().size();
        // set the field null
        member.setPhone(null);

        // Create the Member, which fails.

        restMemberMockMvc
            .perform(
                post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(member))
            )
            .andExpect(status().isBadRequest());

        List<Member> memberList = memberRepository.findAll();
        assertThat(memberList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllMembers() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        // Get all the memberList
        restMemberMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(member.getId().intValue())))
            .andExpect(jsonPath("$.[*].firstName").value(hasItem(DEFAULT_FIRST_NAME)))
            .andExpect(jsonPath("$.[*].lastName").value(hasItem(DEFAULT_LAST_NAME)))
            .andExpect(jsonPath("$.[*].professionalEmail").value(hasItem(DEFAULT_PROFESSIONAL_EMAIL)))
            .andExpect(jsonPath("$.[*].phone").value(hasItem(DEFAULT_PHONE)));
    }

    @Test
    @Transactional
    void getMember() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        // Get the member
        restMemberMockMvc
            .perform(get(ENTITY_API_URL_ID, member.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(member.getId().intValue()))
            .andExpect(jsonPath("$.firstName").value(DEFAULT_FIRST_NAME))
            .andExpect(jsonPath("$.lastName").value(DEFAULT_LAST_NAME))
            .andExpect(jsonPath("$.professionalEmail").value(DEFAULT_PROFESSIONAL_EMAIL))
            .andExpect(jsonPath("$.phone").value(DEFAULT_PHONE));
    }

    @Test
    @Transactional
    void getNonExistingMember() throws Exception {
        // Get the member
        restMemberMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewMember() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        int databaseSizeBeforeUpdate = memberRepository.findAll().size();

        // Update the member
        Member updatedMember = memberRepository.findById(member.getId()).get();
        // Disconnect from session so that the updates on updatedMember are not directly saved in db
        em.detach(updatedMember);
        updatedMember
            .firstName(UPDATED_FIRST_NAME)
            .lastName(UPDATED_LAST_NAME)
            .professionalEmail(UPDATED_PROFESSIONAL_EMAIL)
            .phone(UPDATED_PHONE);

        restMemberMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedMember.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedMember))
            )
            .andExpect(status().isOk());

        // Validate the Member in the database
        List<Member> memberList = memberRepository.findAll();
        assertThat(memberList).hasSize(databaseSizeBeforeUpdate);
        Member testMember = memberList.get(memberList.size() - 1);
        assertThat(testMember.getFirstName()).isEqualTo(UPDATED_FIRST_NAME);
        assertThat(testMember.getLastName()).isEqualTo(UPDATED_LAST_NAME);
        assertThat(testMember.getProfessionalEmail()).isEqualTo(UPDATED_PROFESSIONAL_EMAIL);
        assertThat(testMember.getPhone()).isEqualTo(UPDATED_PHONE);
    }

    @Test
    @Transactional
    void putNonExistingMember() throws Exception {
        int databaseSizeBeforeUpdate = memberRepository.findAll().size();
        member.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMemberMockMvc
            .perform(
                put(ENTITY_API_URL_ID, member.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(member))
            )
            .andExpect(status().isBadRequest());

        // Validate the Member in the database
        List<Member> memberList = memberRepository.findAll();
        assertThat(memberList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchMember() throws Exception {
        int databaseSizeBeforeUpdate = memberRepository.findAll().size();
        member.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMemberMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(member))
            )
            .andExpect(status().isBadRequest());

        // Validate the Member in the database
        List<Member> memberList = memberRepository.findAll();
        assertThat(memberList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamMember() throws Exception {
        int databaseSizeBeforeUpdate = memberRepository.findAll().size();
        member.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMemberMockMvc
            .perform(
                put(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(member))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Member in the database
        List<Member> memberList = memberRepository.findAll();
        assertThat(memberList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateMemberWithPatch() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        int databaseSizeBeforeUpdate = memberRepository.findAll().size();

        // Update the member using partial update
        Member partialUpdatedMember = new Member();
        partialUpdatedMember.setId(member.getId());

        partialUpdatedMember.firstName(UPDATED_FIRST_NAME);

        restMemberMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMember.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedMember))
            )
            .andExpect(status().isOk());

        // Validate the Member in the database
        List<Member> memberList = memberRepository.findAll();
        assertThat(memberList).hasSize(databaseSizeBeforeUpdate);
        Member testMember = memberList.get(memberList.size() - 1);
        assertThat(testMember.getFirstName()).isEqualTo(UPDATED_FIRST_NAME);
        assertThat(testMember.getLastName()).isEqualTo(DEFAULT_LAST_NAME);
        assertThat(testMember.getProfessionalEmail()).isEqualTo(DEFAULT_PROFESSIONAL_EMAIL);
        assertThat(testMember.getPhone()).isEqualTo(DEFAULT_PHONE);
    }

    @Test
    @Transactional
    void fullUpdateMemberWithPatch() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        int databaseSizeBeforeUpdate = memberRepository.findAll().size();

        // Update the member using partial update
        Member partialUpdatedMember = new Member();
        partialUpdatedMember.setId(member.getId());

        partialUpdatedMember
            .firstName(UPDATED_FIRST_NAME)
            .lastName(UPDATED_LAST_NAME)
            .professionalEmail(UPDATED_PROFESSIONAL_EMAIL)
            .phone(UPDATED_PHONE);

        restMemberMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMember.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedMember))
            )
            .andExpect(status().isOk());

        // Validate the Member in the database
        List<Member> memberList = memberRepository.findAll();
        assertThat(memberList).hasSize(databaseSizeBeforeUpdate);
        Member testMember = memberList.get(memberList.size() - 1);
        assertThat(testMember.getFirstName()).isEqualTo(UPDATED_FIRST_NAME);
        assertThat(testMember.getLastName()).isEqualTo(UPDATED_LAST_NAME);
        assertThat(testMember.getProfessionalEmail()).isEqualTo(UPDATED_PROFESSIONAL_EMAIL);
        assertThat(testMember.getPhone()).isEqualTo(UPDATED_PHONE);
    }

    @Test
    @Transactional
    void patchNonExistingMember() throws Exception {
        int databaseSizeBeforeUpdate = memberRepository.findAll().size();
        member.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMemberMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, member.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(member))
            )
            .andExpect(status().isBadRequest());

        // Validate the Member in the database
        List<Member> memberList = memberRepository.findAll();
        assertThat(memberList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchMember() throws Exception {
        int databaseSizeBeforeUpdate = memberRepository.findAll().size();
        member.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMemberMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(member))
            )
            .andExpect(status().isBadRequest());

        // Validate the Member in the database
        List<Member> memberList = memberRepository.findAll();
        assertThat(memberList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamMember() throws Exception {
        int databaseSizeBeforeUpdate = memberRepository.findAll().size();
        member.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMemberMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(member))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Member in the database
        List<Member> memberList = memberRepository.findAll();
        assertThat(memberList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteMember() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        int databaseSizeBeforeDelete = memberRepository.findAll().size();

        // Delete the member
        restMemberMockMvc
            .perform(delete(ENTITY_API_URL_ID, member.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Member> memberList = memberRepository.findAll();
        assertThat(memberList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
