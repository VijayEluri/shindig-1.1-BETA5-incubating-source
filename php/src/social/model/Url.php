<?php
/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

/**
 * see
 * http://www.opensocial.org/Technical-Resources/opensocial-spec-v081/opensocial-reference#opensocial.Url
 */
class Url extends ListField {
  public $value;
  public $linkText;
  public $type;
  public $primary;

  public function __construct($value, $type, $linkText, $primary = null) {
    $this->value = $value;
    $this->type = $type;
    $this->linkText = $linkText;
    $this->primary = $primary;
  }

  public function getLinkText() {
    return $this->linkText;
  }

  public function setLinkText($linkText) {
    $this->linkText = $linkText;
  }
}
