#!/usr/bin/env bash
#
# Copyright 2020 Fraunhofer Institute for Software and Systems Engineering
#
# Licensed under the Apache License, Version 2.0 (the "License");
# you may not use this file except in compliance with the License.
# You may obtain a copy of the License at
#
#    http://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.
#


set -eo pipefail

export FILE_PATH=./scripts/ci/libraries/

. ${FILE_PATH}libs.sh

function init::setup_env_vars() {
    export TEST_FAILURES=0
    export TEST_SUCCESS=0
    export COLOR_DEFAULT=$'\e[0m'
    export COLOR_GREEN=$'\e[32m'
    export COLOR_RED=$'\e[31m'
    export LINE_BREAK_STAR="********************************************************************************"
    export LINE_BREAK_DASH="--------------------------------------------------------------------------------"
}

init::setup_env_vars
