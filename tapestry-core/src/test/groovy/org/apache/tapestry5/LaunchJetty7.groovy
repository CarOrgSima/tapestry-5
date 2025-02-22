package org.apache.tapestry5;

// Copyright 2011 The Apache Software Foundation
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

import org.apache.tapestry5.test.Jetty7Runner

if (this.args.length != 1) {
  fail "Expects one argument: the path to the web context to execute."
}

new Jetty7Runner(this.args[0], "/", 8080, 9999).start()
