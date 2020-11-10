package controller;

import com.panpawelw.controller.SolutionsAdminPanel;
import mockDAOs.MockSolutionDAO;
import org.junit.Before;
import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockServletConfig;

import static org.junit.Assert.assertEquals;

public class SolutionsAdminPanelTests {

    private MockHttpServletRequest request;
    private MockHttpServletResponse response;
    private MockServletConfig config;
    private SolutionsAdminPanel solutionsAdminPanel;

    @Before
    public void setup() throws Exception {
        request = new MockHttpServletRequest();
        response = new MockHttpServletResponse();
        config = new MockServletConfig();
        solutionsAdminPanel = new SolutionsAdminPanel();
    }

    @Test
    public void solutionsAdminPanelForwardTest() throws Exception {
        solutionsAdminPanel.setSolutionDAO(new MockSolutionDAO());
        solutionsAdminPanel.init(config);
        solutionsAdminPanel.doGet(request, response);
        assertEquals("/jsp/solutionsadminview.jsp", response.getForwardedUrl());
    }
}
