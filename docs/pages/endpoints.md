---
layout: default
title: Endpoints
nav_order: 6
description: ""
permalink: /endpoints
has_children: true
has_toc: true
---

# Data App - Endpoints
{: .fs-9 }

In this document, the different endpoint types of an IDS Data App are described in more detail regarding their meaning and function.
{: .fs-6 .fw-300 }

## Endpoints
Depending on which app type is implemented, different endpoints must be implemented. The following table gives an overview of the different endpoint types available. 

| Type                          | Category      | Multiplicity  |
|:------------------------------|:--------------|:--------------|
| [Input](#input)               | dataflow      | multiple      |
| [Output](#output)             | dataflow      | multiple      |
| [Input Extern](#input_ext)    | dataflow      | multiple      |
| [Output Extern](#output_ext)  | dataflow      | multiple      |
| [Config](#config)             | management    | single        |
| [Status](#status)             | management    | single        |
| [Usage](#usage)               | usage control | single        |
---
