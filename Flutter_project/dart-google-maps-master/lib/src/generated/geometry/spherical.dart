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

part of '../google_maps_geometry.dart';

@JS('google.maps.geometry.spherical')
external Object get _Spherical$namespace;

class Spherical {
  static num? computeArea(
    Object? /*List<LatLng?>?|MVCArray<LatLng?>?*/ path, [
    num? radius,
  ]) =>
      callMethod(_Spherical$namespace, 'computeArea', [path, radius]);
  static num? computeDistanceBetween(
    LatLng? from,
    LatLng? to, [
    num? radius,
  ]) =>
      callMethod(
          _Spherical$namespace, 'computeDistanceBetween', [from, to, radius]);
  static num? computeHeading(LatLng? from, LatLng? to) =>
      callMethod(_Spherical$namespace, 'computeHeading', [from, to]);
  static num? computeLength(
    Object? /*List<LatLng?>?|MVCArray<LatLng?>?*/ path, [
    num? radius,
  ]) =>
      callMethod(_Spherical$namespace, 'computeLength', [path, radius]);
  static LatLng? computeOffset(
    LatLng? from,
    num? distance,
    num? heading, [
    num? radius,
  ]) =>
      callMethod(_Spherical$namespace, 'computeOffset',
          [from, distance, heading, radius]);
  static LatLng? computeOffsetOrigin(
    LatLng? to,
    num? distance,
    num? heading, [
    num? radius,
  ]) =>
      callMethod(_Spherical$namespace, 'computeOffsetOrigin',
          [to, distance, heading, radius]);
  static num? computeSignedArea(
    Object? /*List<LatLng?>?|MVCArray<LatLng?>?*/ loop, [
    num? radius,
  ]) =>
      callMethod(_Spherical$namespace, 'computeSignedArea', [loop, radius]);
  static LatLng? interpolate(LatLng? from, LatLng? to, num? fraction) =>
      callMethod(_Spherical$namespace, 'interpolate', [from, to, fraction]);
}
