// Copyright (c) 2015, Alexandre Ardhuin
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//    http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

part of '../google_maps_places.dart';

@JsName()
@anonymous
abstract class AutocompletePrediction {
  factory AutocompletePrediction() => $js();
  String? description;
  // custom name for matched_substrings
  @JsName('matched_substrings')
  List<PredictionSubstring?>? matchedSubstrings;
  // custom name for place_id
  @JsName('place_id')
  String? placeId;
  // custom name for structured_formatting
  @JsName('structured_formatting')
  StructuredFormatting? structuredFormatting;
  List<PredictionTerm?>? terms;
  List<String?>? types;
  // custom name for distance_meters
  @JsName('distance_meters')
  num? distanceMeters;
}
