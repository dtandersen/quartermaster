!server create able2
> POST /api/servers
> GET /api/servers
{ tenants: [{faction: "warden", tenantId: 11}, {faction: "colonial", tenantId: 12}]}

!create fob
!base create fob
!base create hq
> POST /api/base-creation
> POST /api/bases
{ tenantId: 11, name: "fob" }
{ baseId: 100 }

!want 40 rifle @ FOB
> POST /api/minimum-inventory-levels
{ baseId: 100, itemId: 1111, quantity: 40 }

!inventory 2 rifle @ FOB
!inv 2c rifle @ FOB
> POST /api/inventory
{ baseId: 100, itemId: 1000, quantity: 1 }

!prioritize rifle @ FOB
> POST /api/item-priorities
{ baseId: 100, itemId: 1000, priority: 1 }

!building 2c rifle, 2c he grenade @ HQ
> POST /api/queued-items
> 2c rifle, 2c he grenade will be added to inventory in 7m30s
{ items: [ { itemId: 1000, quantity: "2c" }, { itemId: 1001, quantity: "2c" } ] }

!shipment HQ -> FOB
> POST /api/shipments
{ sourceBaseId: 100, destinationBaseId: 101 }
> Created shipment 1 @ HQ
{ shipmentId: 1, status: "pending" }

!shipment delete 1
DELETE /api/shipments/1

!shipment 1 add 2c rifle
> POST /api/shipments/1/items
{ sourceBaseId: 100, destinationBaseId: 101 }

!ship 1
> POST /api/shipment-status
{ status: "ready" }

!pickup 1
> POST /api/shipment-status
{ status: "delivering" }

!deliver 1
> POST /api/shipment-status
{ status: "delivered" }

!base 
!destroy FOB
> DELETE /api/bases

producing - queued at factory
produced  - in chest at factory
deliver   - transporting from factory -> base
received  - now at base
