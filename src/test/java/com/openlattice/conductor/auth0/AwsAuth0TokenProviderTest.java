/*
 * Copyright (C) 2018. OpenLattice, Inc.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 * You can contact the owner of the copyright at support@openlattice.com
 *
 *
 */

package com.openlattice.conductor.auth0;

import com.google.common.collect.ImmutableSet;
import com.kryptnostic.rhizome.configuration.ConfigurationConstants.Profiles;
import com.openlattice.auth0.AwsAuth0TokenProvider;
import com.openlattice.authentication.Auth0Configuration;
import com.openlattice.conductor.ConductorBootstrap;
import org.apache.commons.lang3.StringUtils;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

/**
 * In order to pass this you must have an auth0.yaml file with correct information for client credentials grants.
 */
public class AwsAuth0TokenProviderTest extends ConductorBootstrap {
    @Test
    @Ignore
    public void testAuth0TokenProvider() {
        if ( ImmutableSet.of( conductor.getContext().getEnvironment().getActiveProfiles() )
                .contains( Profiles.AWS_TESTING_PROFILE ) ) {
            Auth0Configuration configuration = conductor.getContext().getBean( Auth0Configuration.class );
            Assert.assertNotNull( configuration );
            Assert.assertTrue( StringUtils.isNotBlank( configuration.getClientSecret() ) );
            Assert.assertTrue( StringUtils.isNotBlank( configuration.getManagementApiUrl() ) );
            Assert.assertTrue( StringUtils.isNotBlank( configuration.getClientId() ) );
            AwsAuth0TokenProvider provider = new AwsAuth0TokenProvider( configuration );
            Assert.assertTrue( StringUtils.isNotBlank( provider.getToken() ) );
        }
    }
}