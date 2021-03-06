/*
* JBoss, Home of Professional Open Source
* Copyright 2009, Red Hat, Inc. and/or its affiliates, and individual contributors
* by the @authors tag. See the copyright.txt in the distribution for a
* full listing of individual contributors.
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
* http://www.apache.org/licenses/LICENSE-2.0
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/
package org.hibernate.beanvalidation.tck.tests.bootstrap.customprovider;

import java.io.InputStream;
import javax.validation.BootstrapConfiguration;
import javax.validation.Configuration;
import javax.validation.ConstraintValidatorFactory;
import javax.validation.MessageInterpolator;
import javax.validation.ParameterNameProvider;
import javax.validation.TraversableResolver;
import javax.validation.Validation;
import javax.validation.ValidationException;
import javax.validation.ValidatorFactory;
import javax.validation.spi.BootstrapState;
import javax.validation.spi.ConfigurationState;
import javax.validation.spi.ValidationProvider;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.testng.Arquillian;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.test.audit.annotations.SpecAssertion;
import org.jboss.test.audit.annotations.SpecAssertions;
import org.jboss.test.audit.annotations.SpecVersion;
import org.testng.annotations.Test;

import org.hibernate.beanvalidation.tck.util.shrinkwrap.WebArchiveBuilder;

/**
 * @author Hardy Ferentschik
 */
@SpecVersion(spec = "beanvalidation", version = "1.1.0")
public class BootstrapNonAvailableValidationProviderTest extends Arquillian {

	@Deployment
	public static WebArchive createTestArchive() {
		return new WebArchiveBuilder()
				.withTestClass( BootstrapNonAvailableValidationProviderTest.class )
				.withValidationXml( "validation-BootstrapUnknownCustomProviderTest.xml" )
				.build();
	}

	@Test(expectedExceptions = ValidationException.class)
	@SpecAssertions({
			@SpecAssertion(section = "5.5.5", id = "f"),
			@SpecAssertion(section = "9", id = "a")
	})
	public void testUnknownProviderConfiguredInValidationXml() {
		// exception is not thrown until validator factory is being build
		Validation.byDefaultProvider().configure().buildValidatorFactory();
	}

	@Test(expectedExceptions = ValidationException.class)
	@SpecAssertions({
			@SpecAssertion(section = "5.5.5", id = "f"),
			@SpecAssertion(section = "9", id = "a")
	})
	public void testConfiguredValidationProviderIsNotLoadable() {
		Validation.byProvider( DummyValidationProvider.class ).configure();
	}

	/**
	 * A valid validation provider implementing all required interfaces, but instantiation will fail
	 */
	public static class DummyValidationProvider implements ValidationProvider<DummyValidatorConfiguration> {

		public DummyValidationProvider() {
			throw new RuntimeException( "ups" );
		}

		@Override
		public DummyValidatorConfiguration createSpecializedConfiguration(BootstrapState state) {
			return null;
		}

		@Override
		public Configuration<?> createGenericConfiguration(BootstrapState state) {
			return null;
		}

		@Override
		public ValidatorFactory buildValidatorFactory(ConfigurationState configurationState) {
			return null;
		}
	}

	public static class DummyValidatorConfiguration implements Configuration<DummyValidatorConfiguration> {

		@Override
		public DummyValidatorConfiguration ignoreXmlConfiguration() {
			return null;
		}

		@Override
		public DummyValidatorConfiguration messageInterpolator(MessageInterpolator interpolator) {
			return null;
		}

		@Override
		public DummyValidatorConfiguration traversableResolver(TraversableResolver resolver) {
			return null;
		}

		@Override
		public DummyValidatorConfiguration constraintValidatorFactory(ConstraintValidatorFactory constraintValidatorFactory) {
			return null;
		}

		@Override
		public DummyValidatorConfiguration parameterNameProvider(ParameterNameProvider parameterNameProvider) {
			return null;
		}

		@Override
		public DummyValidatorConfiguration addMapping(InputStream stream) {
			return null;
		}

		@Override
		public DummyValidatorConfiguration addProperty(String name, String value) {
			return null;
		}

		@Override
		public MessageInterpolator getDefaultMessageInterpolator() {
			return null;
		}

		@Override
		public TraversableResolver getDefaultTraversableResolver() {
			return null;
		}

		@Override
		public ConstraintValidatorFactory getDefaultConstraintValidatorFactory() {
			return null;
		}

		@Override
		public ParameterNameProvider getDefaultParameterNameProvider() {
			return null;
		}

		@Override
		public BootstrapConfiguration getBootstrapConfiguration() {
			return null;
		}

		@Override
		public ValidatorFactory buildValidatorFactory() {
			return null;
		}
	}
}
