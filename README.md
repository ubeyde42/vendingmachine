# Vending Machine API

Bu proje, **Vending Machine** (Satış Makinesi) sistemini modellemek için bir **RESTful API**'dir. API, kullanıcıların makineye para yüklemelerini, ürün satın almalarını, makinenin bakiyesini kontrol etmelerini ve geri ödeme taleplerini yönetmelerini sağlar. Ayrıca, admin kullanıcılarının ürün eklemeleri de mümkündür.

## Özellikler

- **Makine İşlemleri****
  - Makineye para yatırma
  - Ürün satın alma
  - Makine bakiyesini görüntüleme
  - Geri ödeme talebi
  - Makine durumunu kontrol etme (aktif, refund pending vb.)

- **Admin İşlemleri**
  - Yeni ürün ekleme
  - Ürün stoklarını güncelleme

- **Olay Tabanlı Tasarım (Event-Driven Architecture)**
  - Paranın yatırılması (`MoneyDepositedEvent`)
  - Ürün satın alma (`ProductPurchasedEvent`)
  - Geri ödeme talebi (`RefundRequestedEvent`)
  - Geri ödeme başarılı (`RefundSuccessEvent`)

