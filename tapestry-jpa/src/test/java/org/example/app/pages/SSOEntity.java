// Copyright 2009 The Apache Software Foundation
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
// http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.
package org.example.app.pages;

import java.util.List;

import javax.persistence.PersistenceUnit;

import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SessionState;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.jpa.CommitAfter;
import org.apache.tapestry5.services.Request;
import org.apache.tapestry5.services.Session;
import org.example.app.AppConstants;
import org.example.app.entities.User;
import org.example.app.services.UserDAO;

public class SSOEntity
{
    @SessionState
    @Property
    private User user;

    @Inject
    private UserDAO userDAO;

    @Inject
    private Request request;

    @CommitAfter
    @PersistenceUnit(unitName = AppConstants.TEST_PERSISTENCE_UNIT)
    void onPersistEntity()
    {
        final User user = new User();
        user.setFirstName("name");

        userDAO.add(user);

        this.user = user;
    }

    void onSetToNull()
    {
        user = null;
    }

    void onSetToTransient()
    {
        user = new User();
    }

    @CommitAfter
    @PersistenceUnit(unitName = AppConstants.TEST_PERSISTENCE_UNIT)
    void onDelete()
    {
        final List<User> users = userDAO.findAll();

        userDAO.delete(users.toArray(new User[0]));
    }

    public String getPersistedEntityClassName()
    {
        final Session session = request.getSession(true);

        final Object value = session.getAttribute("sso:" + User.class.getName());

        return value.getClass().getName();
    }
}
