package com.visualpathit.account.controller;

import static org.elasticsearch.common.xcontent.XContentFactory.jsonBuilder;

import com.visualpathit.account.model.User;
import com.visualpathit.account.service.UserService;
import com.visualpathit.account.utils.ElasticsearchUtil;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutionException;

import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Controller for basic Elasticsearch operations for users.
 */
@Controller
public class ElasticSearchController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ElasticSearchController.class);

    private static final String INDEX_USERS = "users";
    private static final String TYPE_USER = "user";

    private static final String VIEW_NAME = "elasticeSearchRes";
    private static final String MODEL_KEY_RESULT = "res";

    private final UserService userService;
    private final ElasticsearchUtil elasticsearchUtil;

    public ElasticSearchController(final UserService userService, final ElasticsearchUtil elasticsearchUtil) {
        this.userService = userService;
        this.elasticsearchUtil = elasticsearchUtil;
    }

    @RequestMapping(value = "/user/elasticsearch", method = RequestMethod.GET)
    public String insert(final Model model) throws IOException {
        final List<User> users = userService.getList();

        for (final User user : users) {
            final IndexResponse response = elasticsearchUtil.transportClient()
                    .prepareIndex(INDEX_USERS, TYPE_USER, String.valueOf(user.getId()))
                    .setSource(jsonBuilder()
                            .startObject()
                            .field("name", user.getUsername())
                            .field("DOB", user.getDateOfBirth())
                            .field("fatherName", user.getFatherName())
                            .field("motherName", user.getMotherName())
                            .field("gender", user.getGender())
                            .field("nationality", user.getNationality())
                            .field("phoneNumber", user.getPhoneNumber())
                            .endObject()
                    )
                    .get();

            LOGGER.debug("Indexed user id={} result={}", user.getId(), response.getResult());
        }

        model.addAttribute(MODEL_KEY_RESULT, "Users indexed");
        return VIEW_NAME;
    }

    @RequestMapping(value = "/rest/users/view/{id}", method = RequestMethod.GET)
    public String view(@PathVariable final String id, final Model model) {
        final GetResponse getResponse = elasticsearchUtil.transportClient()
                .prepareGet(INDEX_USERS, TYPE_USER, id)
                .get();

        final Object name = getResponse.getSource() == null ? null : getResponse.getSource().get("name");
        model.addAttribute(MODEL_KEY_RESULT, name);

        return VIEW_NAME;
    }

    @RequestMapping(value = "/rest/users/update/{id}", method = RequestMethod.GET)
    public String update(@PathVariable final String id, final Model model) throws IOException {
        final UpdateRequest updateRequest = new UpdateRequest()
                .index(INDEX_USERS)
                .type(TYPE_USER)
                .id(id)
                .doc(jsonBuilder()
                        .startObject()
                        .field("gender", "male")
                        .endObject()
                );

        try {
            final UpdateResponse updateResponse = elasticsearchUtil.transportClient().update(updateRequest).get();
            model.addAttribute(MODEL_KEY_RESULT, updateResponse.status().toString());
            return VIEW_NAME;
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
            LOGGER.error("Interrupted updating user id={}", id, ex);
        } catch (ExecutionException ex) {
            LOGGER.error("Execution error updating user id={}", id, ex);
        }

        model.addAttribute(MODEL_KEY_RESULT, "Update failed");
        return VIEW_NAME;
    }

    @RequestMapping(value = "/rest/users/delete/{id}", method = RequestMethod.GET)
    public String delete(@PathVariable final String id, final Model model) {
        final DeleteResponse deleteResponse = elasticsearchUtil.transportClient()
                .prepareDelete(INDEX_USERS, TYPE_USER, id)
                .get();

        model.addAttribute(MODEL_KEY_RESULT, deleteResponse.getResult().toString());
        return VIEW_NAME;
    }
}
