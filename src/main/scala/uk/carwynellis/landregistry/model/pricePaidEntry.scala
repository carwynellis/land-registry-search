package uk.carwynellis.landregistry.model

// TODO - better name for this?
// TODO - typing of fields? - strings for everything for now
// Note the last 2 fields relating to price paid category and record status
// will be ignored if present (record status is only included on monthly files)
case class PricePaidEntry(
  id: String,
  price: String, // TODO - confirm this is never float...
  timestamp: String,
  propertyType: String, // TODO - use enum - D,S,T,F,O (detached, semi, terrace, flat, other)
  newBuild: String, // TODO - map values Y,N to boolean true,false
  tenure: String, // TODO - confirm range of possible values
  address: Address
)

// TODO - review and determine which can be optional
case class Address(
  line1: String,
  line2: String,
  street: String,
  locality: String,
  town: String,
  district: String,
  county: String,
  postcode: String
)

